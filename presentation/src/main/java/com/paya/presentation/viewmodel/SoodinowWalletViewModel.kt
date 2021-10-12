package com.paya.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.WalletHostListRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.callResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SoodinowWalletViewModel @Inject constructor(
   private val hostListUseCase: UseCase<Unit,@JvmSuppressWildcards List<WalletHostListRepoModel>>
) : BaseViewModel() {

    val hostsLiveData = MutableLiveData<Resource<List<WalletHostListRepoModel>>>()

    fun hostsList() {
        viewModelScope.launch {
            showLoading()
            val response = callResource(this@SoodinowWalletViewModel,hostListUseCase.action(Unit))
            hostsLiveData.value = response
            hideLoading()
        }
    }

}