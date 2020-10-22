package com.paya.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
	private val activateUseCase: UseCase<ActivateRepoModel,Any>,
	private val registerUseCase: UseCase<String,RegisterRepoModel>
) : ViewModel() {
	
	val remainingTimeText = MutableLiveData<String>()
	val remainingTime = MutableLiveData(59)
	
	init {
		viewModelScope.launch { setRemainingTime() }
	}
	
	lateinit var phoneNumber: String
	val activationCode = ObservableField<String>()
	
	val status = VolatileLiveData<Resource<Any>>()
	
	fun activate() {
		val activationCode = activationCode.get()
		if (activationCode == null || activationCode.isBlank() || activationCode.length != 5) {
			status.setValue(Resource.error("code is not valid",null))
			return
		}
		viewModelScope.launch(Dispatchers.IO) {
			status.postValue(Resource.loading(null))
			val activateModel = ActivateRepoModel(
				phoneNumber,
				activationCode
			)
			val response = activateUseCase.action(activateModel)
			status.postValue(response)
		}
	}
	
	fun register() {
		if (remainingTime.value != 0)
			return
		viewModelScope.launch(Dispatchers.IO) {
			status.postValue(Resource.loading(null))
			val response = registerUseCase.action("+989$phoneNumber")
			
			if (response.status == Status.SUCCESS)
				status.postValue(Resource.idle(null))
			else
				status.postValue(response)
		}
	}
	
	
	private suspend fun setRemainingTime() {
		val formattedRemainingTime =
			if (remainingTime.value!! >= 10) remainingTime.value.toString()
			else "0${remainingTime.value}"
		remainingTimeText.value = "00:$formattedRemainingTime"
		
		if (remainingTime.value == 0)
			return
		
		delay(1000)
		remainingTime.value = remainingTime.value!! - 1
		
		setRemainingTime()
		
	}
	
}