package com.paya.data.network.remote_api

import com.paya.data.network.apiresponse.ApiResponse
import com.paya.domain.models.remote.*
import retrofit2.http.*

interface WalletService {

    @FormUrlEncoded
    @POST("investment/low_invest/")
    suspend fun buyWallet(
        @Field("investment_value") investmentValue: Long,
        @Field("investment_host_id") hostId: Int
    ): ApiResponse<BaseModel<String>>

    @FormUrlEncoded
    @POST("investment/payment/request/")
    suspend fun walletCharge(
        @Field("charge") charge: Long,
        @Field("callback_url") callbackUrl: String,
        @Field("banking_portal") bankingPortal: String
    ): ApiResponse<BaseModel<WalletChargeRemoteModel>>

    @FormUrlEncoded
    @POST("investment/payment/cash_withdraw_request/")
    suspend fun cashWithdrawRequest(
        @Field("amount") amount: Long
    ): ApiResponse<BaseModel<String>>

    @FormUrlEncoded
    @POST("investment/withdraw_request/{id}")
    suspend fun withdrawRequest(
        @Path("id") id: Int,
        @Field("value") sell: Long
    ): ApiResponse<BaseModel<WalletWithdrawRequestRemoteModel>>

    @GET("investment/pre_withdraw/{id}")
    suspend fun preWithdraw(
        @Path("id") id: Int
    ): ApiResponse<BaseModel<WalletPreWithdrawRemoteModel>>

    @GET("investment/portfolio")
    suspend fun portfolio(): ApiResponse<BaseModel<WalletPortfolioRemoteModel>>

    @GET("investment/bank_portals")
    suspend fun bankPortals(): ApiResponse<BaseModel<List<PortalBankRemoteModel>>>

    @GET("investment/wallet")
    suspend fun wallet(): ApiResponse<BaseModel<WalletValueRemoteModel>>

    @GET("investment/hosts")
    suspend fun hostList(): ApiResponse<BaseModel<List<WalletHostListRemoteModel>>>

    @GET("investment/host_info/{host_id}/")
    suspend fun hostDetail(@Path("host_id") hostId: Int): ApiResponse<BaseModel<WalletHostDetailRemoteModel>>

    @GET("investment/investing_info")
    suspend fun investingInfo(): ApiResponse<BaseModel<InvestingInfoRemoteModel>>

    @GET("investment/pre_invoice/{UUID}/")
    suspend fun getPreInvoice(@Path("UUID") uuid: String): ApiResponse<BaseModel<PreInvoiceRemoteModel>>

    @FormUrlEncoded
    @POST("investment/pre_invoice/")
    suspend fun preInvoice(@Field("host_id") hostId: Int,@Field("price") amount: Long): ApiResponse<BaseModel<PreInvoiceRemoteModel>>
}