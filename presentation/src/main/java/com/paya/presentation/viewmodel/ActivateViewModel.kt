package com.paya.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.paya.domain.models.repo.ActivateRepoModel
import com.paya.domain.models.repo.RegisterRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import com.paya.presentation.utils.VolatileLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ActivateViewModel @ViewModelInject constructor(
	private val activateUseCase: UseCase<ActivateRepoModel,ActivateRepoModel>,
	private val registerUseCase: UseCase<String,RegisterRepoModel>
) : ViewModel() {
	
	private val activateResource = MutableLiveData<Resource<ActivateRepoModel>>()
	private val registerStatus = VolatileLiveData<Resource<RegisterRepoModel>>()
	val remainingTimeText = MutableLiveData<String>()
	val remainingTime = MutableLiveData(59)
	
	init {
		viewModelScope.launch { setRemainingTime() }
	}
	
	lateinit var phoneNumber: String
	val activationCode = ObservableField<String>()
	
	val status: LiveData<Resource<Nothing>> = getStatus()
	
	fun activate() {
		val activationCode = activationCode.get()
		if (activationCode == null || activationCode.isBlank()|| activationCode.length != 5) {
			activateResource.value = Resource.error("code is not valid",null)
			return
		}
		viewModelScope.launch(Dispatchers.IO) {
			activateResource.postValue(Resource.loading(null))
			val activateModel = ActivateRepoModel(
				phoneNumber,
				activationCode
			)
			val response = activateUseCase.action(activateModel)
			activateResource.postValue(response)
		}
	}
	
	fun register() {
		if (remainingTime.value != 0)
			return
		viewModelScope.launch(Dispatchers.IO) {
			registerStatus.postValue(Resource.loading(null))
			val response = registerUseCase.action("+989$phoneNumber")
			registerStatus.postValue(response)
		}
	}
	
	private fun getStatus(): MediatorLiveData<Resource<Nothing>> {
		val result = MediatorLiveData<Resource<Nothing>>()
		result.addSource(activateResource) {
			val resource = activateResource.value ?: return@addSource
			result.value = when (resource.status) {
				Status.SUCCESS -> {
					remainingTime.value = 59
					viewModelScope.launch { setRemainingTime() }
					Resource.success(null)
				}
				Status.ERROR -> Resource.error(it.message ?: "Error",null)
				Status.LOADING -> Resource.loading(null)
			}
		}
		result.addSource(registerStatus) {
			val resource = registerStatus.value ?: return@addSource
			result.value = when (resource.status) {
				Status.ERROR -> Resource.error(it.message ?: "Error",null)
				Status.LOADING -> Resource.loading(null)
				else -> return@addSource
			}
		}
		return result
	}
	
	private suspend fun setRemainingTime() {
		val formattedRemainingTime =
			if (remainingTime.value!! >= 10) remainingTime.value.toString()
			else "0${remainingTime.value}"
		remainingTimeText.value = "00:$formattedRemainingTime"
		
		if(remainingTime.value == 0)
			return
		
		delay(1000)
		remainingTime.value = remainingTime.value!! - 1
		
		setRemainingTime()
		
	}
	
}