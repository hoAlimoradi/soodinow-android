package com.paya.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.paya.domain.models.repo.*
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.ui.activitiesReport.source.InvestmentLogsDataSource
import com.paya.presentation.ui.cashManager.source.HistoryPriceDataSource
import com.paya.presentation.utils.VolatileLiveData
import com.paya.presentation.utils.callResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FinancialReportViewModel @ViewModelInject constructor(
	private val investmentUseCase: UseCase<InvestmentLogsRepoBodyModel,InvestmentLogsRepoModel>
) : BaseViewModel() {
	val loading = VolatileLiveData<Resource<Unit>>()
	val status: Flow<PagingData<InvestmentLogsModel>> = Pager(PagingConfig(pageSize = 25)) {
		InvestmentLogsDataSource(investmentUseCase,this@FinancialReportViewModel)
	}.flow
		.cachedIn(viewModelScope)


	
}