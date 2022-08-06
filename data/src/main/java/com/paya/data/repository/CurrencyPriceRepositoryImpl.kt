package com.paya.data.repository

import com.paya.common.Mapper
import com.paya.data.network.remote_api.CurrencyPriceService
import com.paya.data.sharedpreferences.PreferenceHelper
import com.paya.data.utils.getResourceFromApiResponse
import com.paya.domain.models.remote.CommonMarktDataPriceRemoteModel
import com.paya.domain.models.repo.CurrencyPriceRepoModel
import com.paya.domain.repository.CurrencyPriceRepository
import com.paya.domain.tools.Resource
import javax.inject.Inject

class CurrencyPriceRepositoryImpl @Inject constructor(
	private val cryptoMapper: Mapper< @JvmSuppressWildcards List<CommonMarktDataPriceRemoteModel>,@JvmSuppressWildcards List<CurrencyPriceRepoModel>>,
	private val preferenceHelper: PreferenceHelper,
	private val service: CurrencyPriceService
): CurrencyPriceRepository{
	
	override suspend fun getCurrencyPrice(): Resource<List<CurrencyPriceRepoModel>> {
		val currencyResponse = service.getCommonMarketData(preferenceHelper.getAccessToken())
		return getResourceFromApiResponse(currencyResponse){
			cryptoMapper.map(it.data)
		}
	}
	
}