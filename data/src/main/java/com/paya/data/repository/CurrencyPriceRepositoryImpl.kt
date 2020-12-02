package com.paya.data.repository

import com.paya.common.Mapper
import com.paya.data.network.apiresponse.ApiSuccessResponse
import com.paya.data.network.remote_api.CurrencyPriceService
import com.paya.data.utils.getResourceFromApiResponse
import com.paya.domain.models.remote.CryptoPriceRemoteModel
import com.paya.domain.models.remote.CurrencyPriceRemoteModel
import com.paya.domain.models.repo.CurrencyPriceRepoModel
import com.paya.domain.repository.CurrencyPriceRepository
import com.paya.domain.tools.Resource
import javax.inject.Inject

class CurrencyPriceRepositoryImpl @Inject constructor(
	private val cryptoMapper: Mapper<CryptoPriceRemoteModel,CurrencyPriceRepoModel>,
	private val currencyMapper: Mapper<CurrencyPriceRemoteModel,CurrencyPriceRepoModel>,
	private val service: CurrencyPriceService
): CurrencyPriceRepository{
	
	override suspend fun getCurrencyPrice(): Resource<List<CurrencyPriceRepoModel>> {
		val currencyResponse = service.getCurrencyPrices()
		if (currencyResponse !is ApiSuccessResponse){
				return Resource.error("خطایی رخ داده است", null)
		}
		val cryptoResponse = service.getCryptoPrices()
		if (cryptoResponse !is ApiSuccessResponse){
			return Resource.error("خطایی رخ داده است", null)
		}
		
		val repoModels = mutableListOf<CurrencyPriceRepoModel>()
		repoModels.addAll(
			currencyResponse.body.prices.filter {
				it.name == "دلار" || it.name == "یورو"
			}.map {
				currencyMapper.map(it)
			}
		)
		
		val usdPrice = repoModels.firstOrNull { it.name == "دلار" }?.price
			?: return Resource.error("خطایی رخ داده است", null)
		
		val cryptoRepoModels = mutableListOf<CurrencyPriceRepoModel>()
		cryptoRepoModels.addAll(
			cryptoResponse.body.data.filter {
				it.name == "Bitcoin" || it.name == "Ethereum"
			}.map {
				cryptoMapper.map(it)
			}
		)
		
		for (model in cryptoRepoModels) {
			val price = model.price ?: continue
			model.price = (price * usdPrice)
		}
		repoModels.addAll(cryptoRepoModels)
		
		return Resource.success(repoModels)
	}
	
}