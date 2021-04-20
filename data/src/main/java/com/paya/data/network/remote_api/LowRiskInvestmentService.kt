package com.paya.data.network.remote_api

import com.paya.data.network.apiresponse.ApiResponse
import com.paya.domain.models.remote.*
import retrofit2.http.*

interface LowRiskInvestmentService {

    @FormUrlEncoded
    @POST("order/isin-risk-list/")
    suspend fun getLowRiskInvestment(
        @Header("Authorization") auth: String,
        @Field("price") price: Long,
        @Field("type") type: String
    ): ApiResponse<BaseModel<IsInRiskListRemoteModel>>


    @GET("order/exist_account/")
    suspend fun exitAccount(@Header("Authorization") auth: String, ): ApiResponse<BaseModel<ExitAccountRemoteModel>>


    @POST("order/add-risk-order/")
    suspend fun addRiskOrder(
        @Header("Authorization") auth: String,
        @Body body: AddRiskOrderRemoteBodyModel
    ): ApiResponse<BaseModel<String>>


    @GET("order/get_box_type/")
    suspend fun boxTypes(@Header("Authorization") auth: String, ): ApiResponse<BaseModel<BoxTypeRemoteModel>>


    @GET("order/sell_price/{type}/")
    suspend fun getSellPrice(
        @Header("Authorization") auth: String,
        @Path("type") type: String
    ): ApiResponse<BaseModel<List<List<Float>>>>

    @FormUrlEncoded
    @POST("order/pull_price/{type}/")
    suspend fun setPullPrice(
        @Header("Authorization") auth: String,
        @Path("type") type: String,
        @Field("price") price: Long
    ): ApiResponse<BaseModel<String>>


    @GET("order/total_boxes_value/")
    suspend fun totalBoxValue(@Header("Authorization") auth: String, ): ApiResponse<BaseModel<TotalBoxValueRemoteModel>>

    @GET("/")
    suspend fun getHistoryPrice(
        @Header("Authorization") auth: String,
        @Query("page") page: Int,
        @Query("q") filter: String
    ): ApiResponse<BaseModel<HistoryPriceRemoteModel>>

    @GET("order/investment_logs/")
    suspend fun getInvestmentLogs(
        @Header("Authorization") auth: String,
    ): ApiResponse<BaseModel<List<InvestmentLogsRemoteModel>>>
}