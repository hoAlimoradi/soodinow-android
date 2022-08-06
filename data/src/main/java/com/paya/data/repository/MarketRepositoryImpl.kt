package com.paya.data.repository

import com.paya.common.Mapper
import com.paya.data.network.remote_api.MarketService
import com.paya.data.utils.getResourceFromApiResponse
import com.paya.domain.models.remote.MarketRemoteModel
import com.paya.domain.models.repo.MarketRepoModel
import com.paya.domain.repository.MarketRepository
import com.paya.domain.tools.Resource
import javax.inject.Inject

class MarketRepositoryImpl @Inject constructor(
	private val marketApi: MarketService,
	private val getMarketSmallListMapperRemoteRepo: Mapper<MarketRemoteModel,MarketRepoModel>,
) : MarketRepository {
	
	override suspend fun getSmallListMarket(): Resource<MarketRepoModel> {
		val accountCardApiResponse = marketApi.getSmallListMarket()
		return getResourceFromApiResponse(accountCardApiResponse){
			getMarketSmallListMapperRemoteRepo.map(it.data)
		}
	}
	
	
}