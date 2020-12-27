package com.paya.domain.usecase.order

import com.paya.domain.models.repo.*
import com.paya.domain.repository.LowRiskInvestmentRepository
import com.paya.domain.repository.MarketRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class TotalBoxValueUseCase @Inject constructor(
	private val orderRepository: LowRiskInvestmentRepository
): UseCase<Unit, TotalBoxValueRepoModel> {
	override suspend fun action(param: Unit): Resource<TotalBoxValueRepoModel> {
		return orderRepository.totalBoxValue()
	}
}