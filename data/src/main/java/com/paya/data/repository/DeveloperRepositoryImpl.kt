package com.paya.data.repository

import com.paya.common.Mapper
import com.paya.data.database.developer_name.DeveloperNameDbApi
import com.paya.data.datasource.developer.DeveloperDataSource
import com.paya.data.network.apiresponse.*
import com.paya.data.network.remote_api.DeveloperNameService
import com.paya.domain.models.local.DeveloperNameDbModel
import com.paya.domain.models.remote.DeveloperNameServerModel
import com.paya.domain.models.repo.DeveloperNameRepoModel
import com.paya.domain.repository.DeveloperRepository
import com.paya.domain.tools.Resource
import java.lang.Exception
import javax.inject.Inject

class DeveloperRepositoryImpl @Inject constructor(
	private val developerNet: DeveloperNameService,
	private val mapperNet: Mapper<DeveloperNameServerModel,DeveloperNameRepoModel>,
	private val developerApiDb: DeveloperNameDbApi,
	private val mapperEntityRepo: Mapper<DeveloperNameDbModel?,DeveloperNameRepoModel>,
	private val mapperRepoEntity: Mapper<DeveloperNameRepoModel?,DeveloperNameDbModel>
) : DeveloperRepository {
	
	override suspend fun getDeveloperNameFromNet(): Resource<DeveloperNameRepoModel> {
		return when(val developerName = developerNet.getName()){
			is ApiEmptyResponse -> Resource.success(null)
			is ApiFarabiTokenResponse -> Resource.fatabiToken(null)
			is ApiSuccessResponse -> Resource.success(mapperNet.map(developerName.body))
			is ApiErrorResponse -> Resource.error(developerName.errorMessage, null)
			is ApiUnAuthorizedResponse -> Resource.unAuthorized(developerName.errorMessage,null)
		}
	}
	
	override suspend fun deleteDeveloperNameDb() {
		developerApiDb.delete()
	}
	
	override suspend fun getDeveloperNameDb(): Resource<DeveloperNameRepoModel> {
		return try {
			val developerName = developerApiDb.get()
			Resource.success(mapperEntityRepo.map(developerName))
		} catch (e: Exception) {
			Resource.error(
				e.message ?: "unknown error",
				null
			)
		}
	}
	
	override suspend fun insertDeveloperNameDb(param: DeveloperNameRepoModel) {
		developerApiDb.insert(mapperRepoEntity.map(param))
	}
	
}