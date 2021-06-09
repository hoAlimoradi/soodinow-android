package com.paya.presentation.viewmodel

import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.AddRiskOrderRepoBodyModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.SingleLiveEvent
import com.paya.presentation.utils.callResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
@HiltViewModel
class CreateAccountRulesViewModel @Inject constructor(
	private val addRiskOrderUseCase: UseCase<AddRiskOrderRepoBodyModel,String>
) : BaseViewModel() {
	val status = SingleLiveEvent<Resource<String>>()
	fun exitAccount(riskType: String,price: Long) {
		viewModelScope.launch {
			showLoading()
			
			val response = callResource(this@CreateAccountRulesViewModel,addRiskOrderUseCase.action(
				AddRiskOrderRepoBodyModel(
					riskType,
					price
				)
			))
			status.postValue(response)
			hideLoading()
		}
	}


}