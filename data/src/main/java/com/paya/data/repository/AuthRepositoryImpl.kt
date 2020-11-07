package com.paya.data.repository

import com.paya.common.Mapper
import com.paya.data.database.userInfo.UserInfoDbApi
import com.paya.data.network.apiresponse.ApiEmptyResponse
import com.paya.data.network.apiresponse.ApiErrorResponse
import com.paya.data.network.apiresponse.ApiSuccessResponse
import com.paya.data.network.remote_api.AuthService
import com.paya.data.sharedpreferences.PreferenceHelper
import com.paya.domain.models.local.UserInfoDbModel
import com.paya.domain.models.remote.AccessTokenRemoteModel
import com.paya.domain.models.remote.RegisterRemoteModel
import com.paya.domain.models.remote.SetPasswordRemoteModel
import com.paya.domain.models.repo.UserInfoRepoModel
import com.paya.domain.models.repo.RegisterRepoModel
import com.paya.domain.models.repo.SetPasswordRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import java.lang.Exception
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
	private val authNet: AuthService,
	private val registerMapperRemoteRepo: Mapper<RegisterRemoteModel,RegisterRepoModel>,
	private val userInfoMapperRemoteRepo: Mapper<AccessTokenRemoteModel,UserInfoRepoModel>,
	private val userInfoMapperRepoEntity: Mapper<UserInfoRepoModel?,UserInfoDbModel>,
	private val userInfoMapperEntityRepo: Mapper<UserInfoDbModel?,UserInfoRepoModel>,
	private val setPasswordRemoteRepoMapper: Mapper<SetPasswordRemoteModel,SetPasswordRepoModel>,
	private val userInfoDbApi: UserInfoDbApi,
	private val preferenceHelper: PreferenceHelper
) : AuthRepository {
	
	override suspend fun register(phoneNumber: String): Resource<RegisterRepoModel> {
		return when(val registerModel = authNet.register(phoneNumber)){
			is ApiEmptyResponse -> Resource.success(null)
			is ApiSuccessResponse -> Resource.success(registerMapperRemoteRepo.map(registerModel.body.data))
			is ApiErrorResponse -> Resource.error(registerModel.errorMessage, null)
		}
	}
	
	override suspend fun activate(phoneNumber: String, code: String): Resource<UserInfoRepoModel> {
		return when(val activateModel = authNet.activate(phoneNumber, code)){
			is ApiEmptyResponse -> Resource.success(null)
			is ApiSuccessResponse -> Resource.success(userInfoMapperRemoteRepo.map(activateModel.body.data))
			is ApiErrorResponse -> Resource.error(activateModel.errorMessage, null)
		}
	}
	
	override suspend fun login(username: String,password: String): Resource<UserInfoRepoModel> {
		return when(val loginModel = authNet.login(username, password)){
			is ApiEmptyResponse -> Resource.success(null)
			is ApiSuccessResponse -> Resource.success(userInfoMapperRemoteRepo.map(loginModel.body.data))
			is ApiErrorResponse -> Resource.error(loginModel.errorMessage, null)
		}
	}
	
	override suspend fun updateAccessToken(accessToken: String) {
		preferenceHelper.setAccessToken(accessToken)
	}
	
	override suspend fun updateIsPasswordSet(isPasswordSet: Boolean) {
		userInfoDbApi.updateIsPasswordSet(isPasswordSet)
	}
	
	override suspend fun updateIsHintShowed(isHintShowed: Boolean) {
		userInfoDbApi.updateIsHintShowed(isHintShowed)
	}
	
	override suspend fun getUserInfo(): Resource<UserInfoRepoModel> {
		return try {
			val userInfo = userInfoDbApi.getSingle()
			Resource.success(userInfoMapperEntityRepo.map(userInfo))
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