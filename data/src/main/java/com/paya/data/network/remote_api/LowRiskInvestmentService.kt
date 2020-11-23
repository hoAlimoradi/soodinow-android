package com.paya.data.network.remote_api

import com.paya.data.network.apiresponse.ApiResponse
import com.paya.domain.models.remote.*
import retrofit2.http.*

interface LowRiskInvestmentService {
	
	@FormUrlEncoded
	@POST("order/isin-risk-list/")
	suspend fun getLowRiskInvestment(
		@Field("price") price: Long,
		@Field("type") type: String
	): ApiResponse<BaseModel<IsInRiskListRemoteModel>>
	
	
	@GET("order/exist_account/")
	suspend fun exitAccount(): ApiResponse<BaseModel<ExitAccountRemoteModel>>
	
	
	
	@POST("order/add-risk-order/")
	suspend fun addRiskOrder(
		@Body body: AddRiskOrderRemoteBodyModel
	): ApiResponse<BaseModel<List<AddRiskOrderItem>>>
	
}