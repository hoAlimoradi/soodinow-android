package com.paya.data.network.remote_api

import com.paya.data.network.apiresponse.ApiResponse
import com.paya.domain.models.remote.ActivateRemoteModel
import com.paya.domain.models.remote.BaseModel
import com.paya.domain.models.remote.RegisterRemoteModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {
	
	@FormUrlEncoded
	@POST("auth/register/")
	suspend fun register(
		@Field("username") username: String
	): ApiResponse<BaseModel<RegisterRemoteModel>>
	
	@FormUrlEncoded
	@POST("auth/activate/")
	suspend fun activate(
		@Field("username") username: String,
		@Field("code") code: String
	): ApiResponse<BaseModel<ActivateRemoteModel>>
	
}