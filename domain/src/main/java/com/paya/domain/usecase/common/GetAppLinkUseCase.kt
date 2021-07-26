package com.paya.domain.usecase.common

import com.paya.domain.models.repo.GetAppLinkRepoModel
import com.paya.domain.repository.CommonRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject


class GetAppLinkUseCase @Inject constructor(
    private val commonRepository: CommonRepository
): UseCase<Unit, GetAppLinkRepoModel> {
    override suspend fun action(param: Unit): Resource<GetAppLinkRepoModel> {
        return commonRepository.getAppLink()
    }
}