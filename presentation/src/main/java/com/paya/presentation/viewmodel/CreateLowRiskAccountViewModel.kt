package com.paya.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.IsInRiskListRepoModel
import com.paya.domain.models.repo.LowRiskStockRequest
import com.paya.domain.repository.LowRiskInvestmentRepository
import com.paya.domain.tools.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateLowRiskAccountViewModel @ViewModelInject constructor(
	private val lowRiskInvestmentRepository: LowRiskInvestmentRepository
) : ViewModel() {
	
	val lowRiskResource = MutableLiveData<Resource<IsInRiskListRepoModel>>()
	
	fun getLowRiskStocks(type: String,price: Long) {
		lowRiskResource.value = Resource.loading(null)
		viewModelScope.launch(Dispatchers.IO) {
			val response = lowRiskInvestmentRepository.getLowRiskStocks(
				LowRiskStockRequest(type,price)
			)
			lowRiskResource.postValue(response)
		}
		
	}
	
}