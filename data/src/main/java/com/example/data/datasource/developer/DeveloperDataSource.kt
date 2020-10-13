package com.example.data.datasource.developer

import com.example.domain.models.repo.DeveloperNameRepoModel

interface DeveloperDataSource{
	suspend fun getNameDeveloperDb() : DeveloperNameRepoModel
	suspend fun insertNameDeveloperDb(param : DeveloperNameRepoModel)
	suspend fun deleteNameDeveloperDb()
	suspend fun getNameDeveloperNet() : DeveloperNameRepoModel
}