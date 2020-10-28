package com.paya.data.repository

import com.paya.common.Mapper
import com.paya.data.database.userInfo.UserInfoDbApi
import com.paya.data.network.apiresponse.ApiEmptyResponse
import com.paya.data.network.apiresponse.ApiErrorResponse
import com.paya.data.network.apiresponse.ApiSuccessResponse
import com.paya.data.network.remote_api.AccountService
import com.paya.data.network.remote_api.AuthService
import com.paya.data.network.remote_api.MarketService
import com.paya.domain.models.local.UserInfoDbModel
import com.paya.domain.models.remote.*
import com.paya.domain.models.repo.*
import com.paya.domain.repository.AccountRepository
import com.paya.domain.repository.AuthRepository
import com.paya.domain.repository.MarketRepository
import com.paya.domain.tools.Resource
import java.lang.Exception
import javax.inject.Inject

class MarketRepositoryImpl @Inject constructor(
	private val marketApi: MarketService,
	private val getMarketSmallListMapperRemoteRepo: Mapper<MarketRemoteModel,MarketRepoModel>,
) : MarketRepository {
	
	override suspend fun getSmallListMarket(): Resource<MarketRepoModel> {
		return when (val accountCardModel = marketApi.getSmallListMarket()) {
			is ApiEmptyResponse -> Resource.success(null)
			is ApiSuccessResponse -> Resource.success(getMarketSmallListMapperRemoteRepo.map(accountCardModel.body.data))
			is ApiErrorResponse -> Resource.error(accountCardModel.errorMessage,null)
		}
	}
	
	
}