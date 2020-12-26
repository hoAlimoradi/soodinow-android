package com.paya.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.BoxTypeRepoModel

import com.paya.domain.models.repo.PricingCash
import com.paya.domain.models.repo.PullPriceBodyRepoModel
import com.paya.domain.models.repo.TotalBoxValueRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import com.paya.domain.usecase.order.PullPriceUseCase

import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.Utils
import com.paya.presentation.utils.VolatileLiveData
import com.paya.presentation.utils.callResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CashManagerViewModel @ViewModelInject constructor(
    private val boxTypesUseCase: UseCase<Unit, BoxTypeRepoModel>,
    private val sellPriceUseCase: UseCase<String, @JvmSuppressWildcards List<Long>>,
    private val pullPricePriceUseCase: UseCase<PullPriceBodyRepoModel, String>,
    private val totalBoxValueUseCase: UseCase<Unit, TotalBoxValueRepoModel>
) : BaseViewModel() {
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

    init {
        minSeek.set(0)
        maxSeek.set(0)
        price.set(0)
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
        boxTypes()
        totalBoxValue()


    }

    fun boxTypes() {
        viewModelScope.launch(Dispatchers.IO) {
            boxTypesStatus.postValue(Resource.loading(null))
            val response = callResource(this@CashManagerViewModel, boxTypesUseCase.action(Unit))
            if (response.status == Status.SUCCESS) {
                response.data?.types?.get(0)?.let {
                    type.set(it.type)
                    getSellPrice()
                }
            }
            boxTypesStatus.postValue(response)
        }

    }

    fun totalBoxValue() {
        viewModelScope.launch(Dispatchers.IO) {
            totalBoxValueStatus.postValue(Resource.loading(null))
            val response = callResource(this@CashManagerViewModel, totalBoxValueUseCase.action(Unit))
            if (response.status == Status.SUCCESS) {
               totalBoxValue.set(response.data?.totalValue)
            }
            totalBoxValueStatus.postValue(response)
        }

    }

    fun getSellPrice() {
        viewModelScope.launch(Dispatchers.IO) {
            sellPriceStatus.postValue(Resource.loading(null))
            val response =
                callResource(this@CashManagerViewModel, sellPriceUseCase.action(type.get()!!))
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
            pullPriceStatus.setValue(Resource.error("type can not be blank", null))
            return
        }

        if (price <= 0f) {
            pullPriceStatus.setValue(Resource.error("price can not be blank", null))
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            pullPriceStatus.postValue(Resource.loading(null))
            val body = PullPriceBodyRepoModel(type, price)
            val response =
                callResource(this@CashManagerViewModel, pullPricePriceUseCase.action(body))

            pullPriceStatus.postValue(response)
        }

    }

}