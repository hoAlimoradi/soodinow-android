package com.paya.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.CheckVersionRepoModel
import com.paya.domain.models.repo.ValidTokenRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.callResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashActivityViewModel @Inject constructor(
    private val checkVersionUseCase: UseCase<String, CheckVersionRepoModel>,
    private val validTokenUseCase: UseCase<Unit, ValidTokenRepoModel>
) : BaseViewModel() {

    val status = MutableLiveData<Resource<CheckVersionRepoModel>>()
    val statusValidToken = MutableLiveData<Resource<ValidTokenRepoModel>>()

    fun checkVersion(version: String) {
        viewModelScope.launch {
            val response =
                callResource(this@SplashActivityViewModel, checkVersionUseCase.action(version))
            status.postValue(response)
        }
    }

    fun validToken() {
        viewModelScope.launch {
            val response =
                callResource(this@SplashActivityViewModel, validTokenUseCase.action(Unit))
            statusValidToken.postValue(response)
        }
    }

}