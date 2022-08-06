package com.paya.domain.usecase.common

import com.paya.domain.models.repo.WhySoodinowModel
import com.paya.domain.repository.CommonRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class GetWhySoodinowsUseCase @Inject constructor(
    private val commonRepository: CommonRepository
): UseCase<Unit, @JvmSuppressWildcards List<WhySoodinowModel>> {
    override suspend fun action(param: Unit): Resource<List<WhySoodinowModel>> {
        return commonRepository.getWhySoodinowList()
    }
}