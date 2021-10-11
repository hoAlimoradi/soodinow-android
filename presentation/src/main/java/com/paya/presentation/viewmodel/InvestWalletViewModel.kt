package com.paya.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.InvestingInfoRepoModel
import com.paya.domain.models.repo.PortalBankRepoModel
import com.paya.domain.models.repo.WalletChargeRepoBodyModel
import com.paya.domain.models.repo.WalletChargeRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val CALLBACK_URL = "app://com.paya.soodinow.wallet"

@HiltViewModel
class InvestWalletViewModel @Inject constructor(
    private val investingInfoUseCase: UseCase<Unit, InvestingInfoRepoModel>,
    private val bankPortalUseCase: UseCase<Unit, @JvmSuppressWildcards List<PortalBankRepoModel>>,
    private val chargeUseCase: UseCase<WalletChargeRepoBodyModel, WalletChargeRepoModel>,
) : BaseViewModel() {
    val bankPortalResource = SingleLiveEvent<Resource<List<PortalBankRepoModel>>>()
    val investingInfoResource = SingleLiveEvent<Resource<InvestingInfoRepoModel>>()
    val chargeResource = SingleLiveEvent<Resource<WalletChargeRepoModel>>()

    init {
        getInvestingInfo()
        getBankPortal()
    }

    fun getInvestingInfo() {
        viewModelScope.launch {
            showLoading()
            val response = investingInfoUseCase.action(Unit)
            investingInfoResource.value = response
            hideLoading()
        }
    }

    fun getBankPortal() {
        viewModelScope.launch {
            showLoading()
            val response = bankPortalUseCase.action(Unit)
            bankPortalResource.value = response
            hideLoading()
        }
    }

    fun charge(price:Long,bankingPortal:String) {
        viewModelScope.launch {
            showLoading()
            var response = chargeUseCase.action(WalletChargeRepoBodyModel(
                price,
                CALLBACK_URL,
                bankingPortal
            ))
            chargeResource.value = response
            hideLoading()
        }
    }

}