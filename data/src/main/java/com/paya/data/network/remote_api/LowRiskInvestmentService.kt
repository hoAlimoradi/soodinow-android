package com.paya.data.network.remote_api

import com.paya.data.network.apiresponse.ApiResponse
import com.paya.domain.models.remote.BaseModel
import com.paya.domain.models.remote.IsInRiskListRemoteModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LowRiskInvestmentService {
	
	@FormUrlEncoded
	@POST("order/isin-risk-list/")
	suspend fun getLowRiskInvestment(
		@Field("price") price: Long,
		@Field("type") type: String
	): ApiResponse<BaseModel<IsInRiskListRemoteModel>>
	
}