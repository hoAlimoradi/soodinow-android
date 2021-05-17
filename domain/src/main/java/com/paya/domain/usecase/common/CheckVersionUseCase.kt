package com.paya.domain.usecase.common

import com.paya.domain.models.repo.*
import com.paya.domain.repository.CommonRepository
import com.paya.domain.repository.LowRiskInvestmentRepository
import com.paya.domain.repository.MarketRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class CheckVersionUseCase @Inject constructor(
	private val commonRepository: CommonRepository
): UseCase<String, CheckVersionRepoModel> {
	override suspend fun action(param: String): Resource<CheckVersionRepoModel> {
		return commonRepository.checkVersion(param)
	}
}