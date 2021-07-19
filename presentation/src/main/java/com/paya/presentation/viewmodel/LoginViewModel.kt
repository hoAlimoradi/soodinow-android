package com.paya.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.LoginRepoModel
import com.paya.domain.models.repo.PerLoginRepoModel
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
	private val loginUseCase: UseCase<String, PerLoginRepoModel>,
	private val authRepository: AuthRepository
) : BaseViewModel() {
	val loginResource = MutableLiveData<Resource<PerLoginRepoModel>>()
	val mobile = MutableLiveData<String>()



	fun login(username: String) {

		viewModelScope.launch {
			showLoading()

			val response = callResource(this@LoginViewModel,loginUseCase.action(username),false)
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