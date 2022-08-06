package com.paya.data.network.remote_api

import com.paya.data.network.apiresponse.ApiResponse
import com.paya.domain.models.remote.*
import retrofit2.Call
import retrofit2.http.*

interface AuthService {
	
	@FormUrlEncoded
	@POST("auth/register/")
	suspend fun register(
		@Field("phone_number") username: String
	): ApiResponse<BaseModel<RegisterRemoteModel>>

	@FormUrlEncoded
	@POST("auth/reset_password/")
	suspend fun resetPassword(
		@Header("Authorization") auth: String,
		@Field("username") username: String
	): ApiResponse<BaseModel<ResetPasswordRemoteModel>>

	@FormUrlEncoded
	@POST("auth/verify/")
	suspend fun activate(
		@Field("username") username: String,
		@Field("phone_number") phoneNumber: String,
		@Field("code") code: String
	): ApiResponse<BaseModel<AccessTokenRemoteModel>>

	@FormUrlEncoded
	@POST("auth/reset_password/activate/")
	suspend fun resetPasswordActivate(
		@Header("Authorization") auth: String,
		@Field("username") username: String,
		@Field("code") code: String
	): ApiResponse<BaseModel<AccessTokenRemoteModel>>

	@FormUrlEncoded
	@PATCH("auth/reset_password/password/")
	suspend fun setResetPassword(
		@Header("Authorization") auth: String,
		@Field("password") password: String
	): ApiResponse<BaseModel<SetResetPasswordRemoteModel>>

	@FormUrlEncoded
	@PATCH("auth/password/")
	suspend fun setPassword(
		@Header("Authorization") auth: String,
		@Field("password") password: String
	): ApiResponse<BaseModel<SetPasswordRemoteModel>>

	@FormUrlEncoded
	@POST("auth/pre_login/")
	suspend fun perLogin(
		@Field("username") username: String
	): ApiResponse<BaseModel<PerLoginRemoteModel>>

	@FormUrlEncoded
	@POST("auth/login/")
	suspend fun login(
		@Field("username") username: String,
		@Field("code") password: String
	): ApiResponse<BaseModel<AccessTokenRemoteModel>>


	@GET("auth/valid_token/")
	suspend fun validToken(
	@Header("Authorization") auth: String,
	): ApiResponse<BaseModel<String>>

	@FormUrlEncoded
	@POST("auth/refresh_token/")
	fun refreshToken(
		@Field("token") token: String
	): Call<BaseModel<AccessTokenRemoteModel>>

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


	@GET("auth/phone_number/reset/")
	suspend fun resetPhone(
		@Header("Authorization") auth: String
	): ApiResponse<BaseModel<String>>

	@FormUrlEncoded
	@POST("auth/phone_number/reset/verify/")
	suspend fun resetPhoneVerify(
		@Header("Authorization") auth: String,
		@Field("phone_number") phoneNumber: String,
		@Field("code") code: String,
	): ApiResponse<BaseModel<ResetPhoneVerifyRemoteModel>>


	@FormUrlEncoded
	@POST("auth/phone_number/reset/activate/")
	suspend fun resetPhoneActivate(
		@Header("Authorization") auth: String,
		@Field("phone_number") phoneNumber: String,
		@Field("code") code: String,
	): ApiResponse<BaseModel<String>>

	@FormUrlEncoded
	@POST("auth/get_auth_link/")
	suspend fun getAutLink(
		@Header("Authorization") auth: String,
		@Field("callback_url") callBackUrl: String
	): ApiResponse<BaseModel<GetAuthLinkRemoteModel>>
}

