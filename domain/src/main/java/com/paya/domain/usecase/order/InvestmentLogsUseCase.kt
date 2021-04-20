package com.paya.domain.usecase.order

import com.paya.domain.models.repo.*
import com.paya.domain.repository.LowRiskInvestmentRepository
import com.paya.domain.repository.MarketRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class InvestmentLogsUseCase @Inject constructor(
	private val orderRepository: LowRiskInvestmentRepository
): UseCase<Unit, @JvmSuppressWildcards List<InvestmentLogsRepoModel>> {
	override suspend fun action(param: Unit): Resource<List<InvestmentLogsRepoModel>> {
		return orderRepository.getInvestmentLogs()
	}
}