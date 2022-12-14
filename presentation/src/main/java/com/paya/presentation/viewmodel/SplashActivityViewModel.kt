package com.paya.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.CheckVersionRepoModel
import com.paya.domain.models.repo.ConfigRepoModel
import com.paya.domain.models.repo.ValidTokenRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.callResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashActivityViewModel @Inject constructor(
    private val checkVersionUseCase: UseCase<String, CheckVersionRepoModel>,
    private val validTokenUseCase: UseCase<Unit, ValidTokenRepoModel>,
    private val configUseCase: UseCase<Unit, ConfigRepoModel>
) : BaseViewModel() {

    val status = MutableLiveData<Resource<CheckVersionRepoModel>>()
    val statusValidToken = MutableLiveData<Resource<ValidTokenRepoModel>>()
    val statusConfig = MutableLiveData<Resource<ConfigRepoModel>>()
    val retryLiveData = MutableLiveData<Int>()
    private var retryApi = 0;

    fun checkVersion(version: String) {
        viewModelScope.launch {
            val response =
                callResource(this@SplashActivityViewModel, checkVersionUseCase.action(version))
            retryApi(response)
            status.postValue(response)
        }
    }

    fun validToken() {
        viewModelScope.launch {
            val response =
                callResource(this@SplashActivityViewModel, validTokenUseCase.action(Unit))
            retryApi(response)
            statusValidToken.postValue(response)
        }
    }

    fun getConfig() {
        viewModelScope.launch {
            val response =
                callResource(this@SplashActivityViewModel, configUseCase.action(Unit))
            retryApi(response)
            statusConfig.postValue(response)
        }
    }

    fun retryApi(resource: Resource<Any>) {
        if (resource.status == Status.ERROR && retryApi <1) {
            viewModelScope.launch {
                delay(2000)
                retryApi += 1
                retryLiveData.postValue(retryApi)
            }
        }
    }
}