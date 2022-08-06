package com.paya.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.local.NationalCodeModel
import com.paya.domain.models.repo.PerLoginRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.SingleLiveEvent
import com.paya.presentation.utils.callResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
	private val loginUseCase: UseCase<String, PerLoginRepoModel>,
	private val getNationalUseCase: UseCase<Unit, NationalCodeModel>,
	private val authRepository: AuthRepository
) : BaseViewModel() {
	val loginResource = SingleLiveEvent<Resource<PerLoginRepoModel>>()
	val mobile = MutableLiveData<String>()
	val nationalCodeStatus = MutableLiveData<Resource<NationalCodeModel>>()


	init {
	    getNationalCode()
	}

	fun login(username: String) {

		viewModelScope.launch {
			showLoading()

			val response = callResource(this@LoginViewModel,loginUseCase.action(username),false)
			loginResource.postValue(response)
			hideLoading()
		}
	}
	private fun getNationalCode() {
		viewModelScope.launch {
			val response = callResource(this@LoginViewModel,getNationalUseCase.action(Unit),false)
			nationalCodeStatus.postValue(response)
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