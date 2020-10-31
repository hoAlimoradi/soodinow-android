package com.paya.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.AccountCardRepoModel
import com.paya.domain.models.repo.LoginRepoModel
import com.paya.domain.models.repo.MarketRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.utils.VolatileLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
	private val getAccountUseCase: UseCase<Unit,AccountCardRepoModel>,
	private val getMarketSmallListUseCase: UseCase<Unit,MarketRepoModel>
):ViewModel(){
	
	val getAccountCard = VolatileLiveData<Resource<AccountCardRepoModel>>()
	val getMarketSmallList = VolatileLiveData<Resource<MarketRepoModel>>()

	fun getAccount(){
		viewModelScope.launch(Dispatchers.IO) {
			getAccountCard.postValue(Resource.loading(null))
			
			val response = getAccountUseCase.action(Unit)
			getAccountCard.postValue(response)
		}
	}
	fun getMarketList(){
		viewModelScope.launch(Dispatchers.IO) {
			getMarketSmallList.postValue(Resource.loading(null))
			
			val response = getMarketSmallListUseCase.action(Unit)
			getMarketSmallList.postValue(response)
		}
	}
	
}