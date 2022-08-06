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
import com.paya.presentation.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val loginUseCase: UseCase<LoginRepoModel, Any>,
    private val useCaseProfile: UseCase<Unit, ProfileRepoModel>,
    private val getMobileUseCase: UseCase<Unit, String>,
    private val logoutUseCase: UseCase<Unit, Unit>,
    private val authRepository: AuthRepository
) : BaseViewModel() {


    val loading = MutableLiveData<Resource<Any>>()
    val status = MutableLiveData<Resource<ProfileRepoModel>>()
    val statusLogout = SingleLiveEvent<Unit>()
    val mobile = MutableLiveData<String>()
    val loginResource = MutableLiveData<Resource<Any>>()
    fun getMobile() {
        viewModelScope.launch {
            val response = useCaseProfile.action(Unit)
            if (response.status == Status.SUCCESS) {
                response.data?.let {
                    it.mobile?.let { value -> mobile.value = value }
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase.action(Unit).data?.let{statusLogout.postValue(it)}
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
        if (!username.isMobile()) {
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
                username = username.startWithCountryCodeMobile(),
                password = password.md5()!!
            )
            val response = callResource(this@SettingViewModel, loginUseCase.action(loginModel))
            loginResource.postValue(response)
            loading.postValue(response)
            hideLoading()
        }
    }

}