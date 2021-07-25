package com.paya.domain.usecase.common

import com.paya.domain.repository.CommonRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject


class GetAppLinkUseCase @Inject constructor(
    private val commonRepository: CommonRepository
): UseCase<Unit, String> {
    override suspend fun action(param: Unit): Resource<String> {
        return commonRepository.getAppLink()
    }
}