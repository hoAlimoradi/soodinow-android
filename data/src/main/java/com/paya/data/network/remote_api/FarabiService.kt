package com.paya.data.network.remote_api

import com.paya.data.network.apiresponse.ApiResponse
import com.paya.domain.models.remote.BaseModel
import retrofit2.http.*

interface FarabiService {

    @FormUrlEncoded
    @POST("auth/farabi/set_token/")
    suspend fun setToken(
        @Header("Authorization") auth: String,
        @Field("token") token: String
    ): ApiResponse<BaseModel<String>>

    @GET
    suspend fun getUserFarabi(
        @Header("Authorization") auth: String,
        @Url url: String = "https://tse.farabixo.com/api/init/user-info",
    ): ApiResponse<List<*>>

    @FormUrlEncoded
    @POST("auth/farabi/set_info/")
    suspend fun setFarabiInfo(
        @Header("Authorization") auth: String,
        @Field("entry_order_user_id") entryOrderUserId: Double,
        @Field("investor_bourse_code_id") investorBourseCodeId: Double,
        @Field("personage_id") personageId: Double,
        @Field("user_name") userName: String,
    ): ApiResponse<BaseModel<String>>
}