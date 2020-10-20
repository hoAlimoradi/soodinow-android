package com.paya.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.ActivateRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActivateViewModel @ViewModelInject constructor(
	private val activateUseCase: UseCase<ActivateRepoModel, ActivateRepoModel>
): ViewModel(){
	
	lateinit var phoneNumber: String
	val activationCode = ObservableField<String>()
	
	val activateResource = MutableLiveData<Resource<ActivateRepoModel>>()
	
	fun activate(){
		val activationCode = activationCode.get()
		if (activationCode == null || activationCode.length != 4) {
			activateResource.value = Resource.error("code is not valid", null)
			return
		}
		viewModelScope.launch (Dispatchers.IO) {
			activateResource.postValue(Resource.loading(null))
			val activateModel = ActivateRepoModel(
				phoneNumber,
				activationCode
			)
			val response = activateUseCase.action(activateModel)
			activateResource.postValue(response)
		}
	}
	
}