package com.paya.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.paya.domain.models.repo.*

import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import com.paya.domain.usecase.order.PullPriceUseCase

import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.ui.cashManager.source.HistoryPriceDataSource
import com.paya.presentation.utils.Utils
import com.paya.presentation.utils.VolatileLiveData
import com.paya.presentation.utils.callResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class CashHistoryManagerViewModel @ViewModelInject constructor(
    private val historyPriceUseCase: UseCase<HistoryPriceBodyRepoModel, HistoryPriceRepoModel>
) : BaseViewModel() {
    val status: Flow<PagingData<PriceModel>> = Pager(PagingConfig(pageSize = 10)) {
        HistoryPriceDataSource(historyPriceUseCase,"")
    }.flow
        .cachedIn(viewModelScope)

}