package com.paya.data.repository

import com.paya.common.Mapper
import com.paya.data.network.apiresponse.ApiEmptyResponse
import com.paya.data.network.apiresponse.ApiErrorResponse
import com.paya.data.network.apiresponse.ApiSuccessResponse
import com.paya.data.network.remote_api.AuthService
import com.paya.domain.models.remote.ActivateRemoteModel
import com.paya.domain.models.remote.RegisterRemoteModel
import com.paya.domain.models.repo.ActivateRepoModel
import com.paya.domain.models.repo.RegisterRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
	private val authNet: AuthService,
	private val mapperRemoteRepo: Mapper<RegisterRemoteModel,RegisterRepoModel>,
	private val mapperRemoteRepoActivate: Mapper<ActivateRemoteModel,ActivateRepoModel>
) : AuthRepository {
	
	override suspend fun register(phoneNumber: String): Resource<RegisterRepoModel> {
		return when(val registerModel = authNet.register(phoneNumber)){
			is ApiEmptyResponse -> Resource.success(null)
			is ApiSuccessResponse -> Resource.success(mapperRemoteRepo.map(registerModel.body.data))
			is ApiErrorResponse -> Resource.error(registerModel.errorMessage, null)
		}
	}
	
	override suspend fun activate(phoneNumber: String, code: String): Resource<ActivateRepoModel> {
		return when(val activateModel = authNet.activate(phoneNumber, code)){
			is ApiEmptyResponse -> Resource.success(null)
			is ApiSuccessResponse -> Resource.success(mapperRemoteRepoActivate.map(activateModel.body.data))
			is ApiErrorResponse -> Resource.error(activateModel.errorMessage, null)
		}
	}
	
}