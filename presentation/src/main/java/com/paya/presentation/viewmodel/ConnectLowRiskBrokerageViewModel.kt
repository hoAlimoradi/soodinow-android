package com.paya.presentation.viewmodel

import androidx.databinding.ObservableField
import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.AddRiskOrderRepoBodyModel
import com.paya.domain.models.repo.AddRiskOrderRepoItem
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel

import com.paya.presentation.utils.callResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
@HiltViewModel
class ConnectLowRiskBrokerageViewModel @Inject constructor(
	private val addRiskOrderUseCase: UseCase<AddRiskOrderRepoBodyModel,String>
) : BaseViewModel() {
	val status = MutableLiveData<Resource<String>>()
	val tabCheckedIsSoodinow = ObservableField<Boolean>()
	fun exitAccount(riskType: String,price: Long) {
		viewModelScope.launch {
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