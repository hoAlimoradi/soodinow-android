package com.paya.domain.usecase.developer_name

import com.paya.domain.models.repo.DeveloperNameRepoModel
import com.paya.domain.repository.DeveloperRepository
import com.paya.domain.tools.UseCase
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