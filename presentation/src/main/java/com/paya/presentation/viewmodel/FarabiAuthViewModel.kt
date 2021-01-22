package com.paya.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.ActivateRepoModel
import com.paya.domain.models.repo.FarabiTokenRepoModel
import com.paya.domain.models.repo.RegisterRepoModel
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
private val farabiAuthUseCase : UseCase<String, FarabiTokenRepoModel>
) : BaseViewModel() {

    val status=  VolatileLiveData<Resource<FarabiTokenRepoModel>>()

	fun setToken(token:String) {
        if (token.isNullOrBlank()) {
            status.setValue(Resource.error("token can not be blank",null))
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            status.postValue(Resource.loading(null))
            val response = callResource(this@FarabiAuthViewModel,farabiAuthUseCase.action(token))
            status.postValue(response)
        }
    }
}