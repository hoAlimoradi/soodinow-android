package com.paya.domain.usecase.common

import com.paya.domain.models.repo.ConfigRepoModel
import com.paya.domain.repository.CommonRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class GetConfigUseCase @Inject constructor(
    private val commonRepository: CommonRepository
): UseCase<Unit, ConfigRepoModel> {
    override suspend fun action(param: Unit): Resource<ConfigRepoModel> {
        val response = commonRepository.getConfig()
        response.data?.let { commonRepository.saveAppLink(it.appLink) }
        return response
    }
}