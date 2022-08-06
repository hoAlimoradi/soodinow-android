package com.paya.domain.usecase.common

import com.paya.domain.repository.CommonRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class SaveAppLinkUseCase @Inject constructor(
    private val commonRepository: CommonRepository
): UseCase<String, Unit> {
    override suspend fun action(param: String): Resource<Unit> {
        return commonRepository.saveAppLink(param)
    }
}