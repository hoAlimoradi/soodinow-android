package com.paya.presentation.viewmodel


import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
@HiltViewModel
class FinancialReportViewModel @Inject constructor(
	private val investmentUseCase: UseCase<InvestmentLogsRepoBodyModel,InvestmentLogsRepoModel>
) : BaseViewModel() {
	var type: String = ""
	var dateFrom: String = ""
	var dateTo: String = ""
	val loading = MutableLiveData<Resource<Unit>>()
	val status: Flow<PagingData<Any>> = Pager(PagingConfig(pageSize = 25)) {
		InvestmentLogsDataSource(investmentUseCase,this@FinancialReportViewModel)
	}.flow
		.cachedIn(viewModelScope)


	
}