package com.paya.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.AddRiskOrderRepoBodyModel
import com.paya.domain.models.repo.AddRiskOrderRepoModel
import com.paya.domain.models.repo.ExitAccountRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.utils.VolatileLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConnectLowRiskBrokerageViewModel @ViewModelInject constructor(
	private val addRiskOrderUseCase: UseCase<AddRiskOrderRepoBodyModel,ArrayList<AddRiskOrderRepoModel>>
) : ViewModel() {
	val status = VolatileLiveData<Resource<ArrayList<AddRiskOrderRepoModel>>>()
	fun exitAccount(riskType: String,price: Long) {
		viewModelScope.launch(Dispatchers.IO) {
			status.postValue(Resource.loading(null))
			
			val response = addRiskOrderUseCase.action(
				AddRiskOrderRepoBodyModel(
					riskType,
					price
				)
			)
			status.postValue(response)
		}
	}
	
}