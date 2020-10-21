package com.paya.domain.repository

import com.paya.domain.models.repo.AccessTokenRepoModel
import com.paya.domain.models.repo.ActivateRepoModel
import com.paya.domain.models.repo.RegisterRepoModel
import com.paya.domain.models.repo.SetPasswordRepoModel
import com.paya.domain.tools.Resource

interface AuthRepository {
	suspend fun register(phoneNumber: String): Resource<RegisterRepoModel>
	suspend fun activate(
		phoneNumber: String,
		code: String
	): Resource<AccessTokenRepoModel>
	suspend fun updateAccessToken(accessTokenModel: AccessTokenRepoModel)
	suspend fun getAccessToken(): Resource<AccessTokenRepoModel>
	suspend fun setPassword(password: String): Resource<SetPasswordRepoModel>
}