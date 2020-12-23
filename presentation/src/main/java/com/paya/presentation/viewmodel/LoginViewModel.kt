package com.paya.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.LoginRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.VolatileLiveData
import com.paya.presentation.utils.callResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(
	private val loginUseCase: UseCase<LoginRepoModel,Any>,
	private val getMobileUseCase: UseCase<Unit,String>,
	private val authRepository: AuthRepository
):BaseViewModel(){

	val username = ObservableField<String>()
	val password = ObservableField<String>()
	
	val loginResource = VolatileLiveData<Resource<Any>>()
	
	init {
		getMobile()
	}
	private fun getMobile() {
		viewModelScope.launch(Dispatchers.IO) {
			username.set(getMobileUseCase.action(Unit).data?.replace("+989",""))
		}
	}

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
			val response = callResource(this@LoginViewModel,loginUseCase.action(loginModel))
			loginResource.postValue(response)
		}
	}

	fun getPassword(): String?{
		return authRepository.getUserPassword()
	}

	fun getIv(): String?{
		return authRepository.getIV()
	}

}