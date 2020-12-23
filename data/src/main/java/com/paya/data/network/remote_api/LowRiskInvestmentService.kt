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


    @GET("order/get_box_type/")
    suspend fun boxTypes(): ApiResponse<BaseModel<BoxTypeRemoteModel>>


    @GET("order/sell_price/{type}/")
    suspend fun getSellPrice(@Path("type") type: String): ApiResponse<BaseModel<List<List<Long>>>>

    @FormUrlEncoded
    @POST("order/pull_price/{type}/")
    suspend fun setPullPrice(
        @Path("type") type: String,
        @Field("price") price: Long): ApiResponse<BaseModel<Unit>>
}