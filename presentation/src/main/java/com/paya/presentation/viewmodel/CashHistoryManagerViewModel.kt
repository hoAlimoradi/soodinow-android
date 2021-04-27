package com.paya.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.paya.domain.models.repo.*
import com.paya.domain.tools.UseCase

import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.ui.cashManager.source.HistoryPriceDataSource
import kotlinx.coroutines.flow.Flow


class CashHistoryManagerViewModel @ViewModelInject constructor(
    private val historyPriceUseCase: UseCase<HistoryPriceBodyRepoModel, HistoryPriceRepoModel>
) : BaseViewModel() {
    val status: Flow<PagingData<PriceModel>> = Pager(PagingConfig(pageSize = 10)) {
        HistoryPriceDataSource(historyPriceUseCase,"")
    }.flow
        .cachedIn(viewModelScope)

}