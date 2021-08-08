package com.paya.domain.usecase.common

import com.paya.domain.models.repo.AboutUsModel
import com.paya.domain.repository.CommonRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class GetAboutUsContentUseCase @Inject constructor(
    private val commonRepository: CommonRepository
): UseCase<Unit, @JvmSuppressWildcards List<AboutUsModel>> {
    override suspend fun action(param: Unit): Resource<List<AboutUsModel>> {
        return commonRepository.getAboutUsContent()
    }
}