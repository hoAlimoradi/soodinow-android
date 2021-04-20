package com.paya.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.AddRiskOrderRepoBodyModel
import com.paya.domain.models.repo.AddRiskOrderRepoItem
import com.paya.domain.models.repo.InvestmentLogsRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.VolatileLiveData
import com.paya.presentation.utils.callResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FinancialReportViewModel @ViewModelInject constructor(
	private val investmentUseCase: UseCase<Unit,List<InvestmentLogsRepoModel>>
) : BaseViewModel() {
	val status = VolatileLiveData<Resource<List<InvestmentLogsRepoModel>>>()
	init {
	    getInvestmentLogs()
	}
	fun getInvestmentLogs() {
		viewModelScope.launch(Dispatchers.IO) {
			status.postValue(Resource.loading(null))
			
			val response = callResource(this@FinancialReportViewModel,investmentUseCase.action(Unit))
			status.postValue(response)
		}
	}
	
}