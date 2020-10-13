package com.example.data.repository

import com.example.data.datasource.developer.DeveloperDataSource
import com.example.domain.models.repo.DeveloperNameRepoModel
import com.example.domain.repository.DeveloperRepository
import javax.inject.Inject

class DeveloperRepositoryImpl @Inject constructor(
	private val developerDataSource: DeveloperDataSource
) : DeveloperRepository {
	
	override suspend fun getDeveloperNameFromNet(): DeveloperNameRepoModel =
		developerDataSource.getNameDeveloperNet()
	
	override suspend fun deleteDeveloperNameDb() {
		developerDataSource.deleteNameDeveloperDb()
	}
	
	override suspend fun getDeveloperNameDb(): DeveloperNameRepoModel =
		developerDataSource.getNameDeveloperDb()
	
	override suspend fun insertDeveloperNameDb(param: DeveloperNameRepoModel) {
		developerDataSource.insertNameDeveloperDb(param)
	}
	
	
}