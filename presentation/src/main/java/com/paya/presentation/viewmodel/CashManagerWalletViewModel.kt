package com.paya.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.ExitAccountRepoModel
import com.paya.domain.models.repo.WalletPreWithdrawRepoModel
import com.paya.domain.models.repo.WalletWithdrawRequestBodyRepoModel
import com.paya.domain.models.repo.WalletWithdrawRequestRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.SingleLiveEvent
import com.paya.presentation.utils.callResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CashManagerWalletViewModel @Inject constructor(
    private val walletWithdrawRequestUseCase: UseCase<WalletWithdrawRequestBodyRepoModel, WalletWithdrawRequestRepoModel>,
    private val walletPerWithdrawUseCase: UseCase<Int, WalletPreWithdrawRepoModel>,
    private val existAccountUseCase: UseCase<String, ExitAccountRepoModel>
) : BaseViewModel() {
    val existAccount = MutableLiveData<Resource<ExitAccountRepoModel>>()
    val sellPriceStatus = MutableLiveData<Resource<List<Long>>>()
    val pullPriceStatus = SingleLiveEvent<Resource<WalletWithdrawRequestRepoModel>>()
    var cardId = 0
    var price: Long = 0
    var type: String = "Fixed"
    var subType: String = "no_risk"
    var minSeek: Long = 0
    var maxSeek: Long = 0
    var totalBoxValue: Long = 0
    var priceType = PriceTypeWallet.deposit

    init {
        priceType = PriceTypeWallet.deposit
        cardId = 0
        minSeek = 0
        maxSeek = 0
        price = 0
        type = "Fixed"
        subType = "no_risk"
        totalBoxValue = 0
        getExistAccount()

    }

    fun getExistAccount() {
        showLoading()
        viewModelScope.launch {
            existAccount.postValue(
                callResource(
                    this@CashManagerWalletViewModel,
                    existAccountUseCase.action("")
                )
            )
            hideLoading()
        }
    }


    fun withdrawRequest(price: Long) {
        if (price <= 0) {
            showError("مبلغ  مجاز نمیباشد")
            return
        }
        viewModelScope.launch {
            showLoading()
            val response = callResource(
                this@CashManagerWalletViewModel, walletWithdrawRequestUseCase.action(
                    WalletWithdrawRequestBodyRepoModel(
                        cardId,
                        price
                    )
                )
            )
            pullPriceStatus.postValue(response)
            hideLoading()
        }


    }

    enum class PriceTypeWallet {
        deposit,
        withdrawal
    }
}