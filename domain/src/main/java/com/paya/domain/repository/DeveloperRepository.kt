package com.paya.domain.repository

import com.paya.domain.models.repo.DeveloperNameRepoModel

interface DeveloperRepository {
    suspend fun getDeveloperNameFromNet(): DeveloperNameRepoModel
    suspend fun deleteDeveloperNameDb()
    suspend fun getDeveloperNameDb(): DeveloperNameRepoModel
    suspend fun insertDeveloperNameDb(param : DeveloperNameRepoModel)
}