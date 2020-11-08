package com.paya.data.repository

import com.paya.common.Mapper
import com.paya.data.database.userInfo.UserInfoDbApi
import com.paya.data.network.remote_api.AuthService
import com.paya.data.sharedpreferences.PreferenceHelper
import com.paya.data.utils.getResourceFromApiResponse
import com.paya.domain.models.local.UserInfoDbModel
import com.paya.domain.models.remote.AccessTokenRemoteModel
import com.paya.domain.models.remote.RegisterRemoteModel
import com.paya.domain.models.remote.SetPasswordRemoteModel
import com.paya.domain.models.repo.RegisterRepoModel
import com.paya.domain.models.repo.SetPasswordRepoModel
import com.paya.domain.models.repo.UserInfoRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
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
		val registerApiResponse = authNet.register(phoneNumber)
		return getResourceFromApiResponse(registerApiResponse){
			registerMapperRemoteRepo.map(it.data)
		}
	}
	
	override suspend fun activate(phoneNumber: String, code: String): Resource<UserInfoRepoModel> {
		val activateApiResponse = authNet.activate(phoneNumber, code)
		return getResourceFromApiResponse(activateApiResponse){
			userInfoMapperRemoteRepo.map(it.data)
		}
	}
	
	override suspend fun login(username: String,password: String): Resource<UserInfoRepoModel> {
		val loginApiResponse = authNet.login(username, password)
		return getResourceFromApiResponse(loginApiResponse){
			userInfoMapperRemoteRepo.map(it.data)
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
		val setPasswordApiResponse = authNet.setPassword(password)
		return getResourceFromApiResponse(setPasswordApiResponse){
			setPasswordRemoteRepoMapper.map(it.data)
		}
	}
	
}