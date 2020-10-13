package com.example.data.datasource.developer

import com.example.common.Mapper
import com.example.data.database.developer_name.DeveloperNameDbApi
import com.example.data.network.remote_api.DeveloperNameService
import com.example.domain.models.local.DeveloperNameDbModel
import com.example.domain.models.remote.DeveloperNameServerModel
import com.example.domain.models.repo.DeveloperNameRepoModel
import javax.inject.Inject


class DeveloperDataSourceImpl @Inject constructor(
	private val developerNet : DeveloperNameService,
	private val mapperNet: Mapper<DeveloperNameServerModel, DeveloperNameRepoModel>,
	private val developerApiDb: DeveloperNameDbApi,
	private val mapperEntityRepo: Mapper<DeveloperNameDbModel?, DeveloperNameRepoModel>,
	private val mapperRepoEntity: Mapper<DeveloperNameRepoModel?, DeveloperNameDbModel>
) : DeveloperDataSource {
	
	override suspend fun getNameDeveloperDb(): DeveloperNameRepoModel =
		mapperEntityRepo.map(developerApiDb.get())
	
	override suspend fun insertNameDeveloperDb(param: DeveloperNameRepoModel) {
		developerApiDb.insert(mapperRepoEntity.map(param))
	}
	
	override suspend fun deleteNameDeveloperDb() {
		developerApiDb.delete()
	}
	
	override suspend fun getNameDeveloperNet(): DeveloperNameRepoModel =
		mapperNet.map(developerNet.getName())
}