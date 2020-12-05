package com.paya.domain.repository

import com.paya.domain.models.repo.*
import com.paya.domain.tools.Resource

interface AuthRepository {
	suspend fun register(phoneNumber: String): Resource<RegisterRepoModel>
	suspend fun activate(
		phoneNumber: String,
		code: String
	): Resource<UserInfoRepoModel>
	suspend fun login(
		username: String,
		password: String
	): Resource<UserInfoRepoModel>
	suspend fun updateAccessToken(accessToken: String)
	suspend fun getMobile() : Resource<String>
	suspend fun setMobile(mobile: String)
	suspend fun updateIsPasswordSet(isPasswordSet: Boolean)
	suspend fun updateIsHintShowed(isHintShowed: Boolean)
	suspend fun getUserInfo(): Resource<UserInfoRepoModel>
	suspend fun setPassword(password: String): Resource<SetPasswordRepoModel>
	suspend fun updateProfile(body: ProfileBodyRepoModel): Resource<ProfileRepoModel>
	suspend fun getProfile(): Resource<ProfileRepoModel>
	suspend fun changePassword(changePasswordRepoBodyModel: ChangePasswordRepoBodyModel) : Resource<ChangePasswordRepoModel>
}