package com.paya.domain.repository

import com.paya.domain.models.repo.DeveloperNameRepoModel
import com.paya.domain.tools.Resource

interface DeveloperRepository {
    suspend fun getDeveloperNameFromNet(): Resource<DeveloperNameRepoModel>
    suspend fun deleteDeveloperNameDb()
    suspend fun getDeveloperNameDb(): Resource<DeveloperNameRepoModel>
    suspend fun insertDeveloperNameDb(param : DeveloperNameRepoModel)
}