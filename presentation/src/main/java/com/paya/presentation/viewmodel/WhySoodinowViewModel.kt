package com.paya.presentation.viewmodel

import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.*
import com.paya.domain.repository.CommonRepository
import com.paya.domain.repository.CurrencyPriceRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.SingleLiveEvent
import com.paya.presentation.utils.callResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
@HiltViewModel
class WhySoodinowViewModel @Inject constructor(
    private val getWhySoodinowsUseCase: UseCase<Unit, List<WhySoodinowModel>>
) : BaseViewModel() {

    val whySoodinowListMutableLiveData = MutableLiveData<Resource<List<WhySoodinowModel>>>()

    fun getWhySoodinows() {
        viewModelScope.launch {

            val response  = callResource(this@WhySoodinowViewModel,getWhySoodinowsUseCase.action(Unit))
            whySoodinowListMutableLiveData.value = response
        }

    }

}