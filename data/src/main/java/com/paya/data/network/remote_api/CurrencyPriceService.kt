package com.paya.data.network.remote_api

import com.paya.data.network.apiresponse.ApiResponse
import com.paya.domain.models.remote.BaseModel
import com.paya.domain.models.remote.CryptoPriceRemoteModel
import com.paya.domain.models.remote.CurrencyRemoteModel
import retrofit2.http.GET
import retrofit2.http.Url

interface CurrencyPriceService {

	@GET
	suspend fun getCurrencyPrices(
		@Url url: String = "https://hamyarandroid.com/api?t=currency",
	): ApiResponse<CurrencyRemoteModel>
	
	@GET
	suspend fun getCryptoPrices(
		@Url url: String = "http://www.megaweb.ir/api/digital-money",
	): ApiResponse<BaseModel<List<CryptoPriceRemoteModel>>>
	
}