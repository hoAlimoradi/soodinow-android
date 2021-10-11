package com.paya.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.*
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.callResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OpenSoodinowAutomaticViewModel @Inject constructor(
    private val hostDetailUseCase: UseCase<Int, WalletHostDetailRepoModel>,
    private val preInvoiceUseCase: UseCase<PreInvoiceBodyRepoModel, PreInvoiceRepoModel>,
    private val walletChargeUseCase: UseCase<WalletChargeRepoBodyModel, WalletChargeRepoModel>,
) : BaseViewModel() {

    var hostId = -1

    var hostLiveData = MutableLiveData<Resource<WalletHostDetailRepoModel>>()
    var preInvoiceLiveData = MutableLiveData<Resource<PreInvoiceRepoModel>>()
    val walletChargeLiveData = MutableLiveData<Resource<WalletChargeRepoModel>>()
    fun hostDetail() {
        viewModelScope.launch {
            showLoading()
            var response =
                callResource(this@OpenSoodinowAutomaticViewModel, hostDetailUseCase.action(hostId))
            hostLiveData.value = response
            hideLoading()
        }
    }

    fun preInvoice(price: Long) {
        viewModelScope.launch {
            showLoading()
            var response =
                callResource(this@OpenSoodinowAutomaticViewModel, preInvoiceUseCase.action(PreInvoiceBodyRepoModel(
                    hostId,
                    price
                )))
            preInvoiceLiveData.value = response
            hideLoading()
        }
    }

    fun charge(price:Long) {
        viewModelScope.launch {
            showLoading()
            var response = callResource(
                this@OpenSoodinowAutomaticViewModel, walletChargeUseCase.action(
                    WalletChargeRepoBodyModel(
                        price,
                        DEEPLINK,
                        "jibimo"
                    )
                )
            )
            walletChargeLiveData.value = response
            hideLoading()
        }
    }

}