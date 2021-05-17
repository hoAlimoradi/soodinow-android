package com.paya.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.LoginRepoModel
import com.paya.domain.models.repo.ProfileRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.domain.usecase.auth.GetProfileUseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.VolatileLiveData
import com.paya.presentation.utils.callResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingViewModel @ViewModelInject constructor(
	private val loginUseCase: UseCase<LoginRepoModel,Any>,
	private val useCaseProfile: UseCase<Unit,ProfileRepoModel>,
	private val getMobileUseCase: UseCase<Unit,String>,
	private val authRepository: AuthRepository
) : BaseViewModel() {

	init {
		getMobile()
	}
	val loading = MutableLiveData<Resource<Any>>()
	val status = VolatileLiveData<Resource<ProfileRepoModel>>()
	val mobile = ObservableField<String>()
	val loginResource = VolatileLiveData<Resource<Any>>()
	private fun getMobile() {
		viewModelScope.launch(Dispatchers.IO) {
			mobile.set(getMobileUseCase.action(Unit).data)
		}
	}
	fun getProfile() {
		/*viewModelScope.launch(Dispatchers.IO) {
			loading.postValue(Resource.loading(null))
			val response  = callResource(this@SettingViewModel,useCaseProfile.action(Unit))
			status.postValue(response)
			loading.postValue(response)
		}*/
		
	}

	fun setPassword(password: String?){
		authRepository.setUserPassword(password)
	}

	fun getPassword(): String? = authRepository.getUserPassword()

	fun setIv(iv: String){
		authRepository.setIV(iv)
	}

	fun login(username: String, password: String){
		if (username.isNullOrBlank()) {
			loginResource.setValue(Resource.error("لطفا نام کاربری را وارد کنید",null))
			return
		}
		if (username.length != 11){
			loginResource.setValue(Resource.error("نام کاربری وارد شده اشتباه است",null))
			return
		}
		if (password.isNullOrBlank()) {
			loginResource.setValue(Resource.error("لطفا رمز عبور را وارد کنید",null))
			return
		}

		viewModelScope.launch(Dispatchers.IO) {
			loading.postValue(Resource.loading(null))
			val loginModel = LoginRepoModel(
				username = "+98${username.substring(1, username.length)}",
				password = password
			)
			val response = callResource(this@SettingViewModel,loginUseCase.action(loginModel))
			loginResource.postValue(response)
			loading.postValue(response)
		}
	}

}