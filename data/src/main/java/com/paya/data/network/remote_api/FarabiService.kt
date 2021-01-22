package com.paya.data.network.remote_api

import com.paya.data.network.apiresponse.ApiResponse
import com.paya.domain.models.remote.AccountCardRemoteModel
import com.paya.domain.models.remote.BaseModel
import com.paya.domain.models.remote.MarketRemoteModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface FarabiService {

    @FormUrlEncoded
    @POST("auth/farabi/set_token")
    suspend fun setToken(@Field("token") token: String): ApiResponse<BaseModel<String>>
}