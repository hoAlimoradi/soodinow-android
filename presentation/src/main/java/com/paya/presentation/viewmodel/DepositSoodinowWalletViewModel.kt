package com.paya.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.*
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.callResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val DEEPLINK = "app://com.paya.soodinow.invest"

@HiltViewModel
class DepositSoodinowWalletViewModel @Inject constructor(
    private val walletValueUseCase: UseCase<Unit, WalletValueRepoModel>,
    private val getPreInvoiceUseCase: UseCase<Unit, PreInvoiceRepoModel>,
    private val walletChargeUseCase: UseCase<WalletChargeRepoBodyModel, WalletChargeRepoModel>,
    private val walletBuyUseCase: UseCase<WalletBuyRepoBodyModel, WalletBuyRepoModel>,
    private val bankPortalUseCase: UseCase<Unit, @JvmSuppressWildcards List<PortalBankRepoModel>>,
) : BaseViewModel() {

    val bankPortalResource = MutableLiveData<Resource<List<PortalBankRepoModel>>>()
    val walletValeLiveData = MutableLiveData<Resource<WalletValueRepoModel>>()
    val getPreInvoiceLiveData = MutableLiveData<Resource<PreInvoiceRepoModel>>()
    val walletChargeLiveData = MutableLiveData<Resource<WalletChargeRepoModel>>()
    val walletBuyLiveData = MutableLiveData<Resource<WalletBuyRepoModel>>()
    val viewStateLiveData = MutableLiveData<Int>()
    var chargeValue = 0L
    var walletValue = 0L
    var investmentValue = 0L
    var viewState = 0
    var hostId = -1

    init {
        getBankPortal()
    }

    fun getFirstApi() {
        walletValue()
        getPreInvoice()
    }

    fun getBankPortal() {
        viewModelScope.launch {
            showLoading()
            val response = callResource(
                this@DepositSoodinowWalletViewModel, bankPortalUseCase.action(Unit)
            )
            bankPortalResource.value = response
            hideLoading()
        }
    }

    fun walletValue() {
        viewModelScope.launch {
            showLoading()
            val response =
                callResource(this@DepositSoodinowWalletViewModel, walletValueUseCase.action(Unit))
            if (response.status == Status.SUCCESS) {
                walletValue = response.data?.balance ?: 0L
            }
            walletValeLiveData.value = response
            viewState()
            hideLoading()
        }
    }

    fun getPreInvoice() {
        viewModelScope.launch {
            showLoading()
            val response =
                callResource(this@DepositSoodinowWalletViewModel, getPreInvoiceUseCase.action(Unit))
            if (response.status == Status.SUCCESS) {
                investmentValue = response.data?.price ?: 0L
                hostId = response.data?.hostId ?: -1
            }
            getPreInvoiceLiveData.value = response
            viewState()
            hideLoading()
        }
    }

    private fun viewState() {
        if (getPreInvoiceLiveData.value?.status == Status.SUCCESS && walletValeLiveData.value?.status == Status.SUCCESS) {
            chargeValue = if (walletValue >= investmentValue) 0 else   investmentValue - walletValue
            viewState = if (chargeValue == 0L) 1 else 2
            viewStateLiveData.value = viewState
        }
    }

    fun charge(bankingPortal: String) {
        viewModelScope.launch {
            showLoading()
            var response = callResource(
                this@DepositSoodinowWalletViewModel, walletChargeUseCase.action(
                    WalletChargeRepoBodyModel(
                        chargeValue,
                        DEEPLINK,
                        bankingPortal
                    )
                )
            )
            walletChargeLiveData.value = response
            hideLoading()
        }
    }

    fun buy() {
        viewModelScope.launch {
            showLoading()
            var response = callResource(
                this@DepositSoodinowWalletViewModel, walletBuyUseCase.action(
                    WalletBuyRepoBodyModel(
                        investmentValue,
                        hostId
                    )
                )
            )
            walletBuyLiveData.value = response
            hideLoading()
        }
    }
}