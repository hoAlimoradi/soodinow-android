package com.paya.presentation.viewmodel

import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.*
import com.paya.domain.repository.CurrencyPriceRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.SingleLiveEvent
import com.paya.presentation.utils.callResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
@HiltViewModel
class HomeViewModel @Inject constructor(
	private val currencyRepository: CurrencyPriceRepository,
	private val useCaseProfile: UseCase<Unit, ProfileRepoModel>
) : BaseViewModel() {

	val currencyPrice = MutableLiveData<Resource<List<CurrencyPriceRepoModel>>>()
	val statusProfile = SingleLiveEvent<Resource<ProfileRepoModel>>()

	fun getProfile() {
		viewModelScope.launch {
			showLoading()
			val response  = callResource(this@HomeViewModel,useCaseProfile.action(Unit))
			statusProfile.value = response
			hideLoading()
		}

	}

	
	fun getCurrencyPrices() {
		viewModelScope.launch {
			val response= callResource(this@HomeViewModel,currencyRepository.getCurrencyPrice(),false)
			currencyPrice.postValue(response)
		}
	}
	
}