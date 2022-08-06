package com.paya.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.GetAppLinkRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroduceFriendsViewModel @Inject constructor(
    private val getAppLinkUseCase: UseCase<Unit, GetAppLinkRepoModel>
) : BaseViewModel() {

    val status = MutableLiveData<Resource<GetAppLinkRepoModel>>()

    init {
        getAppLink()
    }

    fun getAppLink() {
        viewModelScope.launch {
            status.postValue(getAppLinkUseCase.action(Unit))
        }
    }

}