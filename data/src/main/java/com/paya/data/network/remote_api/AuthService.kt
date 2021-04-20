package com.paya.data.network.remote_api

import com.paya.data.network.apiresponse.ApiResponse
import com.paya.domain.models.remote.*
import retrofit2.http.*

interface AuthService {
	
	@FormUrlEncoded
	@POST("auth/register/")
	suspend fun register(
		@Header("Authorization") auth: String,
		@Field("username") username: String
	): ApiResponse<BaseModel<RegisterRemoteModel>>
	
	@FormUrlEncoded
	@POST("auth/activate/")
	suspend fun activate(
		@Header("Authorization") auth: String,
		@Field("username") username: String,
		@Field("code") code: String
	): ApiResponse<BaseModel<AccessTokenRemoteModel>>
	
	@FormUrlEncoded
	@PATCH("auth/password/")
	suspend fun setPassword(
		@Header("Authorization") auth: String,
		@Field("password") password: String
	): ApiResponse<BaseModel<SetPasswordRemoteModel>>
	
	@FormUrlEncoded
	@POST("auth/login/")
	suspend fun login(
		@Header("Authorization") auth: String,
		@Field("username") username: String,
		@Field("password") password: String
	): ApiResponse<BaseModel<AccessTokenRemoteModel>>
	
	
	@PATCH("auth/profile/update/")
	suspend fun updateProfile(
		@Header("Authorization") auth: String,
		@Body body: ProfileBodyRemoteModel
	): ApiResponse<BaseModel<ProfileRemoteModel>>
	
	
	@GET("auth/profile/")
	suspend fun getProfile(@Header("Authorization") auth: String): ApiResponse<BaseModel<ProfileRemoteModel>>
	
	
	@PATCH("auth/change_password/")
	suspend fun changePassword(
		@Header("Authorization") auth: String,
		@Body body: ChangePasswordRemoteBodyModel
	): ApiResponse<BaseModel<ChangePasswordRemoteModel>>
}

