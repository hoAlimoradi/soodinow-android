package com.paya.presentation.viewmodel

import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.CurrencyPriceRepoModel
import com.paya.domain.repository.CurrencyPriceRepository
import com.paya.domain.tools.Resource
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.callResource
import com.paya.presentation.utils.shared.Point
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
@HiltViewModel
class MarketViewModel @Inject constructor(
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
		showLoading()
		viewModelScope.launch {
			currencyPrice.postValue(callResource(this@MarketViewModel,currencyRepository.getCurrencyPrice()))
			hideLoading()
		}
	}
	
}