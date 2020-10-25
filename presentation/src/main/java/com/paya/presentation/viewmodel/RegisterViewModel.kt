package com.paya.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.RegisterRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.utils.VolatileLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel @ViewModelInject constructor(
	private val registerUseCase: UseCase<String, RegisterRepoModel>
): ViewModel(){
	
	val title = MutableLiveData<String>()
	
	val phoneNumber = ObservableField<String>()
	
	val registerStatus = VolatileLiveData<Resource<RegisterRepoModel>>()
	
	fun setTitle(title: String) {
		this.title.value = title
	}
	
	fun register(){
		val phoneNumber = phoneNumber.get()
		if (phoneNumber == null) {
			registerStatus.setValue(Resource.error("Phone number cannot be empty", null))
			return
		}
		if(phoneNumber.length != 9){
			registerStatus.setValue(Resource.error("Phone number is not valid", null))
			return
		}
		viewModelScope.launch (Dispatchers.IO) {
			registerStatus.postValue(Resource.loading(null))
			val response = registerUseCase.action("+989$phoneNumber")
			registerStatus.postValue(response)
		}
	}
	
}