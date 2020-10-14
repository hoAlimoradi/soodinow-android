package com.paya.data.datasource.developer

import com.paya.common.Mapper
import com.paya.data.database.developer_name.DeveloperNameDbApi
import com.paya.data.network.remote_api.DeveloperNameService
import com.paya.domain.models.local.DeveloperNameDbModel
import com.paya.domain.models.remote.DeveloperNameServerModel
import com.paya.domain.models.repo.DeveloperNameRepoModel
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