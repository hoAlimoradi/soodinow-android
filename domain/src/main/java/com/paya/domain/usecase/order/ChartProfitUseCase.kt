package com.paya.domain.usecase.order

import com.paya.domain.models.repo.*
import com.paya.domain.repository.LowRiskInvestmentRepository
import com.paya.domain.repository.MarketRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class ChartProfitUseCase @Inject constructor(
	private val orderRepository: LowRiskInvestmentRepository
): UseCase<Long, @JvmSuppressWildcards List<ChartProfitRepoModel>> {
	override suspend fun action(param: Long): Resource<List<ChartProfitRepoModel>> {
		return orderRepository.getChartProfit(param)
	}
}