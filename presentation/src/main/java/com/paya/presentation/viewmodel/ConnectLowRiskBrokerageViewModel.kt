package com.paya.presentation.viewmodel

import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.AddInventoryPriceRepoModel
import com.paya.domain.models.repo.AddRiskOrderRepoBodyModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel

import com.paya.presentation.utils.callResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
@HiltViewModel
class ConnectLowRiskBrokerageViewModel @Inject constructor(
	private val addRiskOrderUseCase: UseCase<AddRiskOrderRepoBodyModel,String>
	//private val walletChargeAddInventoryPriceListUseCase: UseCase<Unit,List<AddInventoryPriceRepoModel>>
) : BaseViewModel() {
	val status = MutableLiveData<Resource<String>>()
	val addInventoryPriceListLiveData = MutableLiveData<Resource<List<AddInventoryPriceRepoModel>>>()
	var tabCheckedIsSoodinow : Boolean = false

	fun exitAccount(riskType: String,price: Long) {
		viewModelScope.launch {
			showLoading()
			
			val response = callResource(this@ConnectLowRiskBrokerageViewModel,addRiskOrderUseCase.action(
				AddRiskOrderRepoBodyModel(
					riskType,
					price
				)
			))
			status.postValue(response)
			hideLoading()
		}
	}


	/*fun getAddInventoryPriceList() {
		viewModelScope.launch {
			showLoading()

			val response = callResource(this@ConnectLowRiskBrokerageViewModel,walletChargeAddInventoryPriceListUseCase.action(Unit))
			addInventoryPriceListLiveData.postValue(response)
			hideLoading()
		}
	}*/
	
}