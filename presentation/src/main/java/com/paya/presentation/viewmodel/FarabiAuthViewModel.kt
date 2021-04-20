package com.paya.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.*
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.VolatileLiveData
import com.paya.presentation.utils.callResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FarabiAuthViewModel @ViewModelInject constructor(
private val farabiAuthUseCase : UseCase<String, FarabiTokenRepoModel>,
private val farabiInfoUseCase : UseCase<UserFarabiRepoModel, FarabiInfoRepoModel>,
private val getUserFarabiUseCase : UseCase<String, UserFarabiRepoModel>
) : BaseViewModel() {

    val status=  VolatileLiveData<Resource<FarabiTokenRepoModel>>()
    val statusGetUserFarabi=  VolatileLiveData<Resource<UserFarabiRepoModel>>()
    val statusFarabiInfo=  VolatileLiveData<Resource<FarabiInfoRepoModel>>()

	fun setToken(token:String) {
        if (token.isNullOrBlank()) {
            status.setValue(Resource.error("متاسفانه فارابی دچار مشکل شده است",null))
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            status.postValue(Resource.loading(null))
            val response = callResource(this@FarabiAuthViewModel,farabiAuthUseCase.action(token))
            status.postValue(response)
        }
    }
	fun getUserFarabi(token:String) {
        if (token.isNullOrBlank()) {
            statusGetUserFarabi.setValue(Resource.error("متاسفانه فارابی دچار مشکل شده است",null))
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            statusGetUserFarabi.postValue(Resource.loading(null))
            val response = callResource(this@FarabiAuthViewModel,getUserFarabiUseCase.action(token))
            statusGetUserFarabi.postValue(response)
        }
    }
	fun setFarabiInfo(userFarabiRepoModel: UserFarabiRepoModel) {
        viewModelScope.launch(Dispatchers.IO) {
            statusFarabiInfo.postValue(Resource.loading(null))
            val response = callResource(this@FarabiAuthViewModel,farabiInfoUseCase.action(userFarabiRepoModel))
            statusFarabiInfo.postValue(response)
        }
    }
}