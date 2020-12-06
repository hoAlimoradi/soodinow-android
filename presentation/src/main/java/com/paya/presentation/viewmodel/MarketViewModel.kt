package com.paya.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.CurrencyPriceRepoModel
import com.paya.domain.repository.CurrencyPriceRepository
import com.paya.domain.tools.Resource
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.callResource
import com.paya.presentation.utils.shared.Point
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MarketViewModel @ViewModelInject constructor(
	private val currencyRepository: CurrencyPriceRepository
) : BaseViewModel() {
	
	val pointsLiveData = MutableLiveData<List<Point>>()
	val currencyPrice = MutableLiveData<Resource<List<CurrencyPriceRepoModel>>>()
	
	fun getPoints() {
		val points = mutableListOf<Point>()
		for (i in 0 until 10) {
			val value = (Math.random() * 100).toFloat()
			points.add(Point(i.toFloat(),value))
		}
		pointsLiveData.value = points
	}
	
	fun getCurrencyPrices() {
		currencyPrice.value = Resource.loading(null)
		viewModelScope.launch(Dispatchers.IO) {
			currencyPrice.postValue(callResource(this@MarketViewModel,currencyRepository.getCurrencyPrice()))
		}
	}
	
}