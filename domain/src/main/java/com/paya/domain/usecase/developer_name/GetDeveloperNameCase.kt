package com.paya.domain.usecase.developer_name

import com.paya.domain.models.repo.DeveloperNameRepoModel
import com.paya.domain.repository.DeveloperRepository
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class GetDeveloperNameCase @Inject constructor(
    private val developerRepository : DeveloperRepository
) : UseCase<Unit,DeveloperNameRepoModel> {
    override suspend fun action(
        param : Unit
    ): DeveloperNameRepoModel = developerRepository.getDeveloperNameFromNet()
}