package com.paya.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.LoginRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.callResource
import com.paya.presentation.utils.md5
import com.paya.presentation.utils.startWithCountryCodeMobile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
	private val loginUseCase: UseCase<LoginRepoModel, Any>,
	private val getMobileUseCase: UseCase<Unit, String>,
	private val authRepository: AuthRepository
) : BaseViewModel() {
	val loginResource = MutableLiveData<Resource<Any>>()
	val mobile = MutableLiveData<String>()

	init {
		getMobile()
	}

	private fun getMobile() {
		viewModelScope.launch {
			getMobileUseCase.action(Unit).data?.let {
				mobile.value = it.replaceFirst("+98","0")
			}
		}
	}

	fun login(username: String,password:String) {

		viewModelScope.launch {
			showLoading()
			val loginModel = LoginRepoModel(
				username = username.startWithCountryCodeMobile(),
				password = password.md5()!!
			)
			val response = callResource(this@LoginViewModel,loginUseCase.action(loginModel))
			loginResource.postValue(response)
			hideLoading()
		}
	}

	fun getPassword(): String?{
		return authRepository.getUserPassword()
	}

	fun getIv(): String?{
		return authRepository.getIV()
	}

}