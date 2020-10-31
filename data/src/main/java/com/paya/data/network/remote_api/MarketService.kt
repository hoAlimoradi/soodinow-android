package com.paya.data.network.remote_api

import com.paya.data.network.apiresponse.ApiResponse
import com.paya.domain.models.remote.BaseModel
import com.paya.domain.models.remote.MarketRemoteModel
import retrofit2.http.GET

interface MarketService {
	
	@GET("/")
	suspend fun getSmallListMarket(): ApiResponse<BaseModel<MarketRemoteModel>>
}