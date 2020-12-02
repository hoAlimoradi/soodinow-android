package com.paya.data.network.remote_api

import com.paya.data.network.apiresponse.ApiResponse
import com.paya.domain.models.remote.*
import retrofit2.http.*

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
	): ApiResponse<BaseModel<AccessTokenRemoteModel>>
	
	@FormUrlEncoded
	@PATCH("auth/password/")
	suspend fun setPassword(
		@Field("password") password: String
	): ApiResponse<BaseModel<SetPasswordRemoteModel>>
	
	@FormUrlEncoded
	@POST("auth/login/")
	suspend fun login(
		@Field("username") username: String,
		@Field("password") password: String
	): ApiResponse<BaseModel<AccessTokenRemoteModel>>
	
	
	@PATCH("auth/profile/update/")
	suspend fun updateProfile(
		@Body body: ProfileBodyRemoteModel
	): ApiResponse<BaseModel<ProfileRemoteModel>>
	
	
	@GET("auth/profile/")
	suspend fun getProfile(): ApiResponse<BaseModel<ProfileRemoteModel>>
	
	
	@PATCH("auth/change_password/")
	suspend fun changePassword(
		@Body body: ChangePasswordRemoteBodyModel
	): ApiResponse<BaseModel<ChangePasswordRemoteModel>>
}

