package com.paya.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.*
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.SingleLiveEvent
import com.paya.presentation.utils.callResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CashManagerViewModel @Inject constructor(
    private val sellPriceUseCase: UseCase<String, @JvmSuppressWildcards List<Long>>,
    private val pullPricePriceUseCase: UseCase<PullPriceBodyRepoModel, String>,
    private val addRiskOrderUseCase: UseCase<AddRiskOrderRepoBodyModel, String>,
    private val existAccountUseCase: UseCase<Unit, ExitAccountRepoModel>
) : BaseViewModel() {
    val existAccount = MutableLiveData<Resource<ExitAccountRepoModel>>()
    val sellPriceStatus = MutableLiveData<Resource<List<Long>>>()
    val pullPriceStatus = SingleLiveEvent<Resource<String>>()
    var price : Long = 0
    var type : String = "Fixed"
    var subType : String = "no_risk"
    var minSeek :Long = 0
    var maxSeek : Long = 0
    var totalBoxValue : Long = 0
    var priceType = PriceType.deposit

    init {
        priceType = PriceType.deposit
        minSeek = 0
        maxSeek = 0
        price=0
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
                    this@CashManagerViewModel,
                    existAccountUseCase.action(Unit)
                )
            )
            hideLoading()
        }
    }


    fun getSellPrice(type: String) {
        sellPriceStatus.value?.let {
            if (it.status == Status.LOADING)
                return
        }
        viewModelScope.launch {
            showLoading()
            val response =
                callResource(this@CashManagerViewModel, sellPriceUseCase.action(type))
            if (response.status == Status.SUCCESS) {
                response.data?.size?.let {
                    if (it > 0) {
                        response.data?.get(0)?.let { seek -> minSeek = seek }
                        response.data?.get(it - 1)?.let { seek -> maxSeek = seek }
                    }
                }
            }
            sellPriceStatus.postValue(response)
            hideLoading()
        }

    }


    fun setPullPrice() {


        if (price <= 0f) {
            showError("مبلغ وارد شده اشتباه است")
            return
        }
        if (priceType == PriceType.withdrawal) {
            if (type.isEmpty()) {
                showError("نوع حساب انتخاب تشده است")
                return
            }
            viewModelScope.launch {
                showLoading()
                val body = PullPriceBodyRepoModel(type, price)
                val response =
                    callResource(this@CashManagerViewModel, pullPricePriceUseCase.action(body))

                pullPriceStatus.postValue(response)
                hideLoading()
            }
        } else {
            if (subType.isEmpty()) {
                showError("نوع حساب انتخاب تشده است")
                return
            }

            if (price <= 0) {
                showError("مبلغ  مجاز نمیباشد")
                return
            }
            viewModelScope.launch {
                showLoading()
                val response = callResource(
                    this@CashManagerViewModel, addRiskOrderUseCase.action(
                        AddRiskOrderRepoBodyModel(
                            subType,
                            price
                        )
                    )
                )
                pullPriceStatus.postValue(response)
                hideLoading()
            }
        }

    }

    enum class PriceType {
        deposit,
        withdrawal
    }
}