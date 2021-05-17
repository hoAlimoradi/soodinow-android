package com.paya.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.CheckVersionRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.VolatileLiveData
import com.paya.presentation.utils.callResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashActivityViewModel @ViewModelInject constructor(
    private val checkVersionUseCase: UseCase<String, CheckVersionRepoModel>
) : BaseViewModel() {

    val status = VolatileLiveData<Resource<CheckVersionRepoModel>>()

    fun checkVersion(version: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response =
                callResource(this@SplashActivityViewModel, checkVersionUseCase.action(version))
            status.postValue(response)
        }
    }

}