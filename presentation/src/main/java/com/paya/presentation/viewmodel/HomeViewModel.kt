package com.paya.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.AccountCardRepoModel
import com.paya.domain.models.repo.CurrencyPriceRepoModel
import com.paya.domain.models.repo.ExitAccountRepoModel
import com.paya.domain.models.repo.MarketRepoModel
import com.paya.domain.repository.CurrencyPriceRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.VolatileLiveData
import com.paya.presentation.utils.callResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
	private val getAccountUseCase: UseCase<Unit,AccountCardRepoModel>,
	private val getMarketSmallListUseCase: UseCase<Unit,MarketRepoModel>,
	private val exitAccountUseCase: UseCase<Unit,ExitAccountRepoModel>,
	private val currencyRepository: CurrencyPriceRepository
) : BaseViewModel() {
	
	
	val getAccountCard = VolatileLiveData<Resource<AccountCardRepoModel>>()
	val getMarketSmallList = VolatileLiveData<Resource<MarketRepoModel>>()
	val exitAccountStatus = VolatileLiveData<Resource<ExitAccountRepoModel>>()
	val currencyPrice = VolatileLiveData<Resource<List<CurrencyPriceRepoModel>>>()
	
	fun getAccount() {
		viewModelScope.launch(Dispatchers.IO) {
			getAccountCard.postValue(Resource.loading(null))
			
			val response = callResource(this@HomeViewModel,getAccountUseCase.action(Unit))
			getAccountCard.postValue(response)
		}
	}
	
	fun getMarketList() {
		viewModelScope.launch(Dispatchers.IO) {
			getMarketSmallList.postValue(Resource.loading(null))
			
			val response = callResource(this@HomeViewModel,getMarketSmallListUseCase.action(Unit))
			getMarketSmallList.postValue(response)
		}
	}
	
	fun exitAccount() {
		viewModelScope.launch(Dispatchers.IO) {
			exitAccountStatus.postValue(Resource.loading(null))
			
			val response = callResource(this@HomeViewModel,exitAccountUseCase.action(Unit))
			exitAccountStatus.postValue(response)
		}
	}
	
	fun getCurrencyPrices() {
		currencyPrice.postValue( Resource.loading(null))
		viewModelScope.launch(Dispatchers.IO) {
			val response= callResource(this@HomeViewModel,currencyRepository.getCurrencyPrice())
			currencyPrice.postValue(response)
		}
	}
	
}