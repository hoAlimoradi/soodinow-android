package com.paya.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.*
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.VolatileLiveData
import com.paya.presentation.utils.callResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CashManagerViewModel @ViewModelInject constructor(
    private val boxTypesUseCase: UseCase<Unit, BoxTypeRepoModel>,
    private val sellPriceUseCase: UseCase<String, @JvmSuppressWildcards List<Long>>,
    private val pullPricePriceUseCase: UseCase<PullPriceBodyRepoModel, String>,
    private val totalBoxValueUseCase: UseCase<Unit, TotalBoxValueRepoModel>,
    private val addRiskOrderUseCase: UseCase<AddRiskOrderRepoBodyModel, String>,
    private val existAccountUseCase: UseCase<Unit, ExitAccountRepoModel>
) : BaseViewModel() {
    val existAccount = MutableLiveData<Resource<ExitAccountRepoModel>>()
    val boxTypesStatus = VolatileLiveData<Resource<BoxTypeRepoModel>>()
    val sellPriceStatus = VolatileLiveData<Resource<List<Long>>>()
    val pullPriceStatus = VolatileLiveData<Resource<String>>()
    val totalBoxValueStatus = VolatileLiveData<Resource<TotalBoxValueRepoModel>>()
    val loading = MediatorLiveData<Resource<Nothing>>()
    val price = ObservableField<Long>()
    val type = ObservableField<String>()
    val minSeek = ObservableField<Long>()
    val maxSeek = ObservableField<Long>()
    val totalBoxValue = ObservableField<Long>()
    var priceType =  ObservableField<PriceType>()

    init {
        priceType.set(PriceType.deposit)
        minSeek.set(0)
        maxSeek.set(0)
        price.set(0)
        type.set("no_risk")
        totalBoxValue.set(0)
        loading.addSource(boxTypesStatus) {
            if (boxTypesStatus.value?.status == Status.LOADING || totalBoxValueStatus.value?.status == Status.LOADING) {
                loading.value = Resource.loading(null)
            } else {
                loading.value = Resource.idle(null)
            }
        }
        loading.addSource(pullPriceStatus) {
            if (pullPriceStatus.value?.status == Status.LOADING) {
                loading.value = Resource.loading(null)
            } else {
                loading.value = Resource.idle(null)
            }
        }
        loading.addSource(existAccount) {
            if (existAccount.value?.status == Status.LOADING) {
                loading.value = Resource.loading(null)
            } else {
                loading.value = Resource.idle(null)
            }
        }
        loading.addSource(sellPriceStatus) {
            if (sellPriceStatus.value?.status == Status.LOADING) {
                loading.value = Resource.loading(null)
            } else {
                loading.value = Resource.idle(null)
            }
        }
        loading.addSource(totalBoxValueStatus) {
            if (totalBoxValueStatus.value?.status == Status.LOADING || boxTypesStatus.value?.status == Status.LOADING) {
                loading.value = Resource.loading(null)
            } else {
                loading.value = Resource.idle(null)
            }
        }
        getExistAccount()

    }

    fun getExistAccount() {
        existAccount.value = Resource.loading(null)
        viewModelScope.launch(Dispatchers.IO) {
            existAccount.postValue(
                callResource(
                    this@CashManagerViewModel,
                    existAccountUseCase.action(Unit)
                )
            )
        }
    }


    fun boxTypes() {
        viewModelScope.launch(Dispatchers.IO) {
            boxTypesStatus.postValue(Resource.loading(null))
            val response = callResource(this@CashManagerViewModel, boxTypesUseCase.action(Unit))
            if (response.status == Status.SUCCESS) {
                if (response.data?.types?.size!! > 0)
                    response.data?.types?.get(0).let {
                        if (it != null) {
                            type.set(it.type)
                        }
                        // getSellPrice()
                    }
            }
            boxTypesStatus.postValue(response)
        }

    }

    fun totalBoxValue() {
        viewModelScope.launch(Dispatchers.IO) {
            totalBoxValueStatus.postValue(Resource.loading(null))
            val response =
                callResource(this@CashManagerViewModel, totalBoxValueUseCase.action(Unit))
            if (response.status == Status.SUCCESS) {
                totalBoxValue.set(response.data?.totalValue)
            }
            totalBoxValueStatus.postValue(response)
        }

    }

    fun getSellPrice(type: String) {
        viewModelScope.launch(Dispatchers.IO) {
            sellPriceStatus.postValue(Resource.loading(null))
            val response =
                callResource(this@CashManagerViewModel, sellPriceUseCase.action(type))
            if (response.status == Status.SUCCESS) {
                response.data?.size?.let {
                    if (it > 0) {
                        minSeek.set(response.data?.get(0))
                        maxSeek.set(response.data?.get(it - 1))
                        price.set(response.data?.get(0))
                    }
                }
            }
            sellPriceStatus.postValue(response)
        }

    }

    /*fun getSellPrice() {
        minSeek.set(5009733)
        maxSeek.set(30887961)
        price.set(5009733)

        val list = mutableListOf<Long>(5009733, 8191881, 13217941, 16132461, 28720961, 30887961)
        viewModelScope.launch(Dispatchers.IO) {
            sellPriceStatus.postValue(Resource.success(list))
        }
    }*/

    fun setPullPrice() {
        val type = this.type.get()
        val price: Long = this.price.get()!!
        if (type.isNullOrBlank()) {
            pullPriceStatus.setValue(Resource.error("نوع حساب انتخاب تشده است", null))
            return
        }

        if (price <= 0f) {
            pullPriceStatus.setValue(Resource.error("مبلغ وارد شده اشتباه است", null))
            return
        }
        if (priceType.get() == PriceType.withdrawal) {
            viewModelScope.launch(Dispatchers.IO) {
                pullPriceStatus.postValue(Resource.loading(null))
                val body = PullPriceBodyRepoModel(type, price)
                val response =
                    callResource(this@CashManagerViewModel, pullPricePriceUseCase.action(body))

                pullPriceStatus.postValue(response)
            }
        } else {
            if (price <= 0) {
                pullPriceStatus.setValue(Resource.error("مبلغ  مجاز نمیباشد", null))
                return
            }
            viewModelScope.launch(Dispatchers.IO) {
                pullPriceStatus.postValue(Resource.loading(null))

                val response = callResource(
                    this@CashManagerViewModel, addRiskOrderUseCase.action(
                        AddRiskOrderRepoBodyModel(
                            type,
                            price
                        )
                    )
                )
                pullPriceStatus.postValue(response)
            }
        }

    }

    enum class PriceType {
        deposit,
        withdrawal
    }
}