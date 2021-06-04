package com.paya.presentation.viewmodel

import androidx.databinding.ObservableField
import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.LoginRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.callResource
import com.paya.presentation.utils.md5
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
@HiltViewModel
class LoginViewModel @Inject constructor(
	private val loginUseCase: UseCase<LoginRepoModel,Any>,
	private val getMobileUseCase: UseCase<Unit,String>,
	private val authRepository: AuthRepository
):BaseViewModel(){

	var isUserNameError = false;
	var isPasswordError = false;
	val username = ObservableField<String>()
	val password = ObservableField<String>()
	
	val loginResource = MutableLiveData<Resource<Any>>()
	
	init {
		getMobile()
	}
	private fun getMobile() {
		viewModelScope.launch {
			username.set(getMobileUseCase.action(Unit).data?.replace("+989",""))
		}
	}

	fun login(){
		isUserNameError = false;
		isPasswordError = false;
		val username = username.get()
		val password = password.get()
		if (username.isNullOrBlank()) {
			isUserNameError = true
			loginResource.setValue(Resource.error("لطفا شماره موبایل را وارد کنید",null))
			return
		}
		if (username.length != 9){
			isUserNameError = true
			loginResource.setValue(Resource.error("شماره موبایل وارد شده اشتباه است",null))
			return
		}
		if (password.isNullOrBlank()) {
			isPasswordError = true
			loginResource.setValue(Resource.error("لطفا رمز عبور را وارد کنید",null))
			return
		}
		viewModelScope.launch {
			loginResource.postValue(Resource.loading(null))
			val loginModel = LoginRepoModel(
				username = "+989$username",
				password = password.md5()!!
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