package com.example.domain.repository

import com.example.domain.models.repo.DeveloperNameRepoModel

interface DeveloperRepository {
    suspend fun getDeveloperNameFromNet(): DeveloperNameRepoModel
    suspend fun deleteDeveloperNameDb()
    suspend fun getDeveloperNameDb(): DeveloperNameRepoModel
    suspend fun insertDeveloperNameDb(param : DeveloperNameRepoModel)
}