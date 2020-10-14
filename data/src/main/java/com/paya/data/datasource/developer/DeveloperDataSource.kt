package com.paya.data.datasource.developer

import com.paya.domain.models.repo.DeveloperNameRepoModel

interface DeveloperDataSource{
	suspend fun getNameDeveloperDb() : DeveloperNameRepoModel
	suspend fun insertNameDeveloperDb(param : DeveloperNameRepoModel)
	suspend fun deleteNameDeveloperDb()
	suspend fun getNameDeveloperNet() : DeveloperNameRepoModel
}