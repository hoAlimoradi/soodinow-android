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
	private val currencyRepository: CurrencyPriceRepository
) : BaseViewModel() {

	val currencyPrice = VolatileLiveData<Resource<List<CurrencyPriceRepoModel>>>()
	

	
	fun getCurrencyPrices() {
		currencyPrice.postValue( Resource.loading(null))
		viewModelScope.launch(Dispatchers.IO) {
			val response= callResource(this@HomeViewModel,currencyRepository.getCurrencyPrice())
			currencyPrice.postValue(response)
		}
	}
	
}