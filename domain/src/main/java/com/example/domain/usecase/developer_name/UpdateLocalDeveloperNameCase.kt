package com.example.domain.usecase.developer_name

import com.example.domain.models.repo.DeveloperNameRepoModel
import com.example.domain.repository.DeveloperRepository
import com.example.domain.tools.UseCase
import javax.inject.Inject

class UpdateLocalDeveloperNameCase @Inject constructor(
    private val developerRepository : DeveloperRepository
) : UseCase<DeveloperNameRepoModel, DeveloperNameRepoModel> {
    override suspend fun action(
        param : DeveloperNameRepoModel
    ): DeveloperNameRepoModel {
        developerRepository.deleteDeveloperNameDb()
        developerRepository.insertDeveloperNameDb(param)
        return developerRepository.getDeveloperNameDb()
    }
}