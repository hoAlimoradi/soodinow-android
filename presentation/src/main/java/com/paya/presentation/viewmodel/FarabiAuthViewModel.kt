package com.paya.presentation.viewmodel


import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.*
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.callResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
@HiltViewModel
class FarabiAuthViewModel @Inject constructor(
private val farabiAuthUseCase : UseCase<String, FarabiTokenRepoModel>,
private val farabiInfoUseCase : UseCase<UserFarabiRepoModel, FarabiInfoRepoModel>,
private val getUserFarabiUseCase : UseCase<String, UserFarabiRepoModel>
) : BaseViewModel() {

    val status=  MutableLiveData<Resource<FarabiTokenRepoModel>>()
    val statusGetUserFarabi=  MutableLiveData<Resource<UserFarabiRepoModel>>()
    val statusFarabiInfo=  MutableLiveData<Resource<FarabiInfoRepoModel>>()

	fun setToken(token:String) {
        if (token.isEmpty()) {
            showError("متاسفانه فارابی دچار مشکل شده است")
            return
        }

        viewModelScope.launch {
            showLoading()
            val response = callResource(this@FarabiAuthViewModel,farabiAuthUseCase.action(token))
            status.postValue(response)
            hideLoading()
        }
    }
	fun getUserFarabi(token:String) {
        if (token.isEmpty()) {
            showError("متاسفانه فارابی دچار مشکل شده است")
            return
        }

        viewModelScope.launch {
            showLoading()
            val response = callResource(this@FarabiAuthViewModel,getUserFarabiUseCase.action(token))
            statusGetUserFarabi.postValue(response)
            hideLoading()
        }
    }
	fun setFarabiInfo(userFarabiRepoModel: UserFarabiRepoModel) {
        viewModelScope.launch {
            showLoading()
            val response = callResource(this@FarabiAuthViewModel,farabiInfoUseCase.action(userFarabiRepoModel))
            statusFarabiInfo.postValue(response)
            hideLoading()
        }
    }
}