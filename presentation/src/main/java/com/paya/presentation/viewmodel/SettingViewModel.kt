package com.paya.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.LoginRepoModel
import com.paya.domain.models.repo.ProfileRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.callResource
import com.paya.presentation.utils.md5
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val loginUseCase: UseCase<LoginRepoModel, Any>,
    private val useCaseProfile: UseCase<Unit, ProfileRepoModel>,
    private val getMobileUseCase: UseCase<Unit, String>,
    private val authRepository: AuthRepository
) : BaseViewModel() {


    val loading = MutableLiveData<Resource<Any>>()
    val status = MutableLiveData<Resource<ProfileRepoModel>>()
    val mobile = MutableLiveData<String>()
    val loginResource = MutableLiveData<Resource<Any>>()
    fun getMobile() {
        viewModelScope.launch {
            val response = getMobileUseCase.action(Unit)
            if (response.status == Status.SUCCESS) {
                response.data?.let {
                    mobile.value = it
                }
            }
        }
    }

    fun getProfile() {
        /*viewModelScope.launch {
           showLoading()
            val response  = callResource(this@SettingViewModel,useCaseProfile.action(Unit))
            status.postValue(response)
            loading.postValue(response)
            hideLoading()
        }*/

    }

    fun setPassword(password: String?) {
        authRepository.setUserPassword(password)
    }

    fun getPassword(): String? = authRepository.getUserPassword()

    fun setIv(iv: String) {
        authRepository.setIV(iv)
    }

    fun login(username: String, password: String) {
        if (username.isNullOrBlank()) {
            showError("لطفا نام کاربری را وارد کنید")
            return
        }
        if (username.length != 11) {
            showError("نام کاربری وارد شده اشتباه است")
            return
        }
        if (password.isNullOrBlank()) {
            showError("لطفا رمز عبور را وارد کنید")
            return
        }

        viewModelScope.launch {
            showLoading()
            val loginModel = LoginRepoModel(
                username = "+98${username.substring(1, username.length)}",
                password = password.md5()!!
            )
            val response = callResource(this@SettingViewModel, loginUseCase.action(loginModel))
            loginResource.postValue(response)
            loading.postValue(response)
            hideLoading()
        }
    }

}