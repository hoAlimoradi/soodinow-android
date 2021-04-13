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
	private val authRepository: AuthRepository
) : BaseViewModel() {

	init {
		getProfile()
	}
	val loading = MutableLiveData<Resource<Any>>()
	val status = VolatileLiveData<Resource<ProfileRepoModel>>()
	val mobile = ObservableField<String>()
	val loginResource = VolatileLiveData<Resource<Any>>()

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
			loginResource.setValue(Resource.error("username can not be blank",null))
			return
		}
		if (username.length != 11){
			loginResource.setValue(Resource.error("username is not valid",null))
			return
		}
		if (password.isNullOrBlank()) {
			loginResource.setValue(Resource.error("password can not be blank",null))
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