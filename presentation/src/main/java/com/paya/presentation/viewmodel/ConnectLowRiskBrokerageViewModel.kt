package com.paya.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.AddRiskOrderRepoBodyModel
import com.paya.domain.models.repo.AddRiskOrderRepoItem
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.VolatileLiveData
import com.paya.presentation.utils.callResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConnectLowRiskBrokerageViewModel @ViewModelInject constructor(
	private val addRiskOrderUseCase: UseCase<AddRiskOrderRepoBodyModel,String>
) : BaseViewModel() {
	val status = VolatileLiveData<Resource<String>>()
	val tabCheckedIsSoodinow = ObservableField<Boolean>()
	fun exitAccount(riskType: String,price: Long) {
		viewModelScope.launch(Dispatchers.IO) {
			status.postValue(Resource.loading(null))
			
			val response = callResource(this@ConnectLowRiskBrokerageViewModel,addRiskOrderUseCase.action(
				AddRiskOrderRepoBodyModel(
					riskType,
					price
				)
			))
			status.postValue(response)
		}
	}
	
}