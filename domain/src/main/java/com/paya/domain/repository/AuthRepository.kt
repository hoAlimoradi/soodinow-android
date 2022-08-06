package com.paya.domain.repository

import com.paya.domain.models.local.NationalCodeModel
import com.paya.domain.models.repo.*
import com.paya.domain.tools.Resource

interface AuthRepository {
	suspend fun register(phoneNumber: String): Resource<RegisterRepoModel>
	suspend fun resetPassword(phoneNumber: String): Resource<ResetPasswordRepoModel>
	suspend fun activate(
		username: String,
		phoneNumber: String,
		code: String
	): Resource<UserInfoRepoModel>

	suspend fun activateResetPassword(
		phoneNumber: String,
		code: String
	): Resource<UserInfoRepoModel>

	suspend fun login(
		username: String,
		password: String
	): Resource<UserInfoRepoModel>

	suspend fun perLogin(
		username: String
	): Resource<PerLoginRepoModel>

	suspend fun isLogin(): Resource<Boolean>

	suspend fun validToken(): Resource<ValidTokenRepoModel>

	suspend fun getAuthLink(callBackLink:String): Resource<GetAuthLinkRepoModel>


	suspend fun updateAccessToken(accessToken: String)
	suspend fun updateRefreshToken(token: String)
	suspend fun getMobile(): Resource<String>
	suspend fun setMobile(mobile: String)
	fun setNationalCode(code: String)
	fun getNationalCode(): Resource<NationalCodeModel>
	suspend fun updateIsPasswordSet(isPasswordSet: Boolean)
	suspend fun updateIsHintShowed(isHintShowed: Boolean)
	suspend fun getUserInfo(): Resource<UserInfoRepoModel>
	suspend fun setPassword(password: String): Resource<SetPasswordRepoModel>
	suspend fun setResetPassword(password: String): Resource<SetResetPasswordRepoModel>
	suspend fun updateProfile(body: ProfileBodyRepoModel): Resource<ProfileRepoModel>
	suspend fun getProfile(): Resource<ProfileRepoModel>
	suspend fun changePassword(changePasswordRepoBodyModel: ChangePasswordRepoBodyModel): Resource<ChangePasswordRepoModel>
	fun getUserPassword(): String?
	fun setUserPassword(password: String?)
	fun getIV(): String?
	fun setIV(iv: String)

	suspend fun resetPhone(): Resource<ResetPhoneRepoModel>
	suspend fun resetPhoneVerify(phone: String, code: String): Resource<ResetPhoneVerifyRepoModel>
	suspend fun resetPhoneActivate(phone: String, code: String): Resource<ActivateResetPhoneRepoModel>

	suspend fun clearToken() : Resource<Unit>
}