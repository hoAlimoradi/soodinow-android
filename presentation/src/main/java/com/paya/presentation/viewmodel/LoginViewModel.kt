package com.paya.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.LoginRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.utils.VolatileLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(
	private val loginUseCase: UseCase<LoginRepoModel, Any>
):ViewModel(){

	val username = ObservableField<String>()
	val password = ObservableField<String>()
	
	val loginResource = VolatileLiveData<Resource<Any>>()

	fun login(){
		val username = username.get()
		val password = password.get()
		if (username.isNullOrBlank()) {
			loginResource.setValue(Resource.error("username can not be blank",null))
			return
		}
		if (username.length != 9){
			loginResource.setValue(Resource.error("username is not valid",null))
			return
		}
		if (password.isNullOrBlank()) {
			loginResource.setValue(Resource.error("password can not be blank",null))
			return
		}
		viewModelScope.launch(Dispatchers.IO) {
			loginResource.postValue(Resource.loading(null))
			val loginModel = LoginRepoModel(
				username = "+989$username",
				password = password
			)
			val response = loginUseCase.action(loginModel)
			loginResource.postValue(response)
		}
	}
	
}