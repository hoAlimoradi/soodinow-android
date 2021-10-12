package com.paya.data.network.remote_api

import com.paya.data.network.apiresponse.ApiResponse
import com.paya.domain.models.remote.*
import retrofit2.http.*

interface WalletService {

    @FormUrlEncoded
    @POST("investment/low_invest/")
    suspend fun buyWallet(
        @Header("Authorization") auth: String,
        @Field("investment_value") investmentValue: Long,
        @Field("investment_host_id") hostId: Int
    ): ApiResponse<BaseModel<String>>

    @FormUrlEncoded
    @POST("investment/payment/request/")
    suspend fun walletCharge(
        @Header("Authorization") auth: String,
        @Field("charge") charge: Long,
        @Field("callback_url") callbackUrl: String,
        @Field("banking_portal") bankingPortal: String
    ): ApiResponse<BaseModel<WalletChargeRemoteModel>>

    @FormUrlEncoded
    @POST("investment/payment/cash_withdraw_request/")
    suspend fun cashWithdrawRequest(
        @Header("Authorization") auth: String,
        @Field("amount") amount: Long
    ): ApiResponse<BaseModel<String>>

    @FormUrlEncoded
    @POST("investment/withdraw_request/{id}/")
    suspend fun withdrawRequest(
        @Header("Authorization") auth: String,
        @Path("id") id: Int,
        @Field("value") sell: Long
    ): ApiResponse<BaseModel<WalletWithdrawRequestRemoteModel>>

    @GET("investment/pre_withdraw/{id}/")
    suspend fun preWithdraw(
        @Header("Authorization") auth: String,
        @Path("id") id: Int
    ): ApiResponse<BaseModel<WalletPreWithdrawRemoteModel>>

    @GET("investment/portfolio")
    suspend fun portfolio(@Header("Authorization") auth: String,): ApiResponse<BaseModel<WalletPortfolioRemoteModel>>

    @GET("investment/bank_portals")
    suspend fun bankPortals(@Header("Authorization") auth: String,): ApiResponse<BaseModel<List<PortalBankRemoteModel>>>

    @GET("investment/wallet")
    suspend fun wallet(@Header("Authorization") auth: String,): ApiResponse<BaseModel<WalletValueRemoteModel>>

    @GET("investment/hosts")
    suspend fun hostList(@Header("Authorization") auth: String,): ApiResponse<BaseModel<List<WalletHostListRemoteModel>>>

    @GET("investment/host_info/{host_id}/")
    suspend fun hostDetail(@Header("Authorization") auth: String,@Path("host_id") hostId: Int): ApiResponse<BaseModel<WalletHostDetailRemoteModel>>

    @GET("investment/investing_info")
    suspend fun investingInfo(@Header("Authorization") auth: String,): ApiResponse<BaseModel<InvestingInfoRemoteModel>>

    @GET("investment/pre_invoice/{UUID}/")
    suspend fun getPreInvoice(@Header("Authorization") auth: String,@Path("UUID") uuid: String): ApiResponse<BaseModel<PreInvoiceRemoteModel>>

    @FormUrlEncoded
    @POST("investment/pre_invoice/")
    suspend fun preInvoice(@Header("Authorization") auth: String,@Field("host_id") hostId: Int,@Field("price") amount: Long): ApiResponse<BaseModel<PreInvoiceRemoteModel>>

    @GET("investment/exist_account/")
    suspend fun exitAccount(@Header("Authorization") auth: String, ): ApiResponse<BaseModel<ExitAccountRemoteModel>>
}