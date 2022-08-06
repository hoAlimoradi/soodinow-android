package com.paya.data.network.remote_api

import com.paya.data.network.apiresponse.ApiResponse
import com.paya.domain.models.remote.BaseModel
import com.paya.domain.models.remote.CommonMarktDataPriceRemoteModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url

interface CurrencyPriceService {

	@GET("common_market_data/")
	suspend fun getCommonMarketData(@Header("Authorization") auth: String): ApiResponse<BaseModel<List<CommonMarktDataPriceRemoteModel>>>
	

}