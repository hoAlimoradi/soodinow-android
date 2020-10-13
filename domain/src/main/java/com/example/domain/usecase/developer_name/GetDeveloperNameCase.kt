package com.example.domain.usecase.developer_name

import com.example.domain.models.repo.DeveloperNameRepoModel
import com.example.domain.repository.DeveloperRepository
import com.example.domain.tools.UseCase
import javax.inject.Inject

class GetDeveloperNameCase @Inject constructor(
    private val developerRepository : DeveloperRepository
) : UseCase<Unit,DeveloperNameRepoModel> {
    override suspend fun action(
        param : Unit
    ): DeveloperNameRepoModel = developerRepository.getDeveloperNameFromNet()
}