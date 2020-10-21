package com.paya.data.repository

import com.paya.common.Mapper
import com.paya.data.database.accessToken.AccessTokenDbApi
import com.paya.data.network.apiresponse.ApiEmptyResponse
import com.paya.data.network.apiresponse.ApiErrorResponse
import com.paya.data.network.apiresponse.ApiSuccessResponse
import com.paya.data.network.remote_api.AuthService
import com.paya.domain.models.local.AccessTokenDbModel
import com.paya.domain.models.remote.AccessTokenRemoteModel
import com.paya.domain.models.remote.RegisterRemoteModel
import com.paya.domain.models.remote.SetPasswordRemoteModel
import com.paya.domain.models.repo.AccessTokenRepoModel
import com.paya.domain.models.repo.RegisterRepoModel
import com.paya.domain.models.repo.SetPasswordRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import java.lang.Exception
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
	private val authNet: AuthService,
	private val registerMapperRemoteRepo: Mapper<RegisterRemoteModel,RegisterRepoModel>,
	private val accessTokenMapperRemoteRepo: Mapper<AccessTokenRemoteModel,AccessTokenRepoModel>,
	private val accessTokenMapperRepoEntity: Mapper<AccessTokenRepoModel?,AccessTokenDbModel>,
	private val accessTokenMapperEntityRepo: Mapper<AccessTokenDbModel?,AccessTokenRepoModel>,
	private val setPasswordRemoteRepoMapper: Mapper<SetPasswordRemoteModel,SetPasswordRepoModel>,
	private val accessTokenDbApi: AccessTokenDbApi
) : AuthRepository {
	
	override suspend fun register(phoneNumber: String): Resource<RegisterRepoModel> {
		return when(val registerModel = authNet.register(phoneNumber)){
			is ApiEmptyResponse -> Resource.success(null)
			is ApiSuccessResponse -> Resource.success(registerMapperRemoteRepo.map(registerModel.body.data))
			is ApiErrorResponse -> Resource.error(registerModel.errorMessage, null)
		}
	}
	
	override suspend fun activate(phoneNumber: String, code: String): Resource<AccessTokenRepoModel> {
		return when(val activateModel = authNet.activate(phoneNumber, code)){
			is ApiEmptyResponse -> Resource.success(null)
			is ApiSuccessResponse -> Resource.success(accessTokenMapperRemoteRepo.map(activateModel.body.data))
			is ApiErrorResponse -> Resource.error(activateModel.errorMessage, null)
		}
	}
	
	override suspend fun updateAccessToken(accessTokenModel: AccessTokenRepoModel) {
		accessTokenDbApi.update(
			accessTokenMapperRepoEntity.map(accessTokenModel)
		)
	}
	
	override suspend fun getAccessToken(): Resource<AccessTokenRepoModel> {
		return try {
			val accessToken = accessTokenDbApi.getSingle()
			Resource.success(accessTokenMapperEntityRepo.map(accessToken))
		} catch (e: Exception) {
			Resource.error(
				e.message ?: "unknown error",
				null
			)
		}
	}
	
	override suspend fun setPassword(password: String): Resource<SetPasswordRepoModel> {
		return when(val setPasswordModel = authNet.setPassword(password)){
			is ApiEmptyResponse -> Resource.success(null)
			is ApiSuccessResponse -> Resource.success(setPasswordRemoteRepoMapper.map(setPasswordModel.body.data))
			is ApiErrorResponse -> Resource.error(setPasswordModel.errorMessage, null)
		}
	}
	
}