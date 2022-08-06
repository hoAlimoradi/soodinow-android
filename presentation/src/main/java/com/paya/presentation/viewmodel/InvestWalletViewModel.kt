package com.paya.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.*
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.SingleLiveEvent
import com.paya.presentation.utils.callResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val CALLBACK_URL = "app://com.paya.soodinow.wallet"

@HiltViewModel
class InvestWalletViewModel @Inject constructor(
    private val investingInfoUseCase: UseCase<Unit, InvestingInfoRepoModel>,
    private val cashWithdrawRequest: UseCase<Long, CashWithdrawRequestRepoModel>,
    private val bankPortalUseCase: UseCase<Unit, @JvmSuppressWildcards List<PortalBankRepoModel>>,
    private val chargeUseCase: UseCase<WalletChargeRepoBodyModel, WalletChargeRepoModel>,
) : BaseViewModel() {
    val bankPortalResource = MutableLiveData<Resource<List<PortalBankRepoModel>>>()
    val investingInfoResource = MutableLiveData<Resource<InvestingInfoRepoModel>>()
    val cashWithdrawRequestResource = MutableLiveData<Resource<CashWithdrawRequestRepoModel>>()
    val chargeResource = SingleLiveEvent<Resource<WalletChargeRepoModel>>()

    init {
        getInvestingInfo()
        getBankPortal()
    }

    fun getInvestingInfo() {
        viewModelScope.launch {
            showLoading()
            val response = callResource(
                this@InvestWalletViewModel,investingInfoUseCase.action(Unit))
            investingInfoResource.value = response
            hideLoading()
        }
    }

    fun getBankPortal() {
        viewModelScope.launch {
            showLoading()
            val response = callResource(
                this@InvestWalletViewModel,bankPortalUseCase.action(Unit))
            bankPortalResource.value = response
            hideLoading()
        }
    }

    fun charge(price:Long,bankingPortal:String) {
        viewModelScope.launch {
            showLoading()
            var response = callResource(
                this@InvestWalletViewModel,chargeUseCase.action(WalletChargeRepoBodyModel(
                price,
                CALLBACK_URL,
                bankingPortal
            )))
            chargeResource.value = response
            hideLoading()
        }
    }
    fun withdraw(price:Long) {
        viewModelScope.launch {
            showLoading()
            var response = callResource(
                this@InvestWalletViewModel,cashWithdrawRequest.action(price))
            cashWithdrawRequestResource.value = response
            hideLoading()
        }
    }

}