package com.paya.presentation.viewmodel

import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.CheckVersionRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.callResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
@HiltViewModel
class SplashActivityViewModel @Inject constructor(
    private val checkVersionUseCase: UseCase<String, CheckVersionRepoModel>
) : BaseViewModel() {

    val status = MutableLiveData<Resource<CheckVersionRepoModel>>()

    fun checkVersion(version: String) {
        viewModelScope.launch {
            val response =
                callResource(this@SplashActivityViewModel, checkVersionUseCase.action(version))
            status.postValue(response)
        }
    }

}