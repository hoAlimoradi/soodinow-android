package com.paya.domain.usecase.order

import com.paya.domain.models.repo.*
import com.paya.domain.repository.LowRiskInvestmentRepository
import com.paya.domain.repository.MarketRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class AddRiskOrderUseCase @Inject constructor(
	private val orderRepository: LowRiskInvestmentRepository
): UseCase<AddRiskOrderRepoBodyModel, @JvmSuppressWildcards String> {
	override suspend fun action(param: AddRiskOrderRepoBodyModel): Resource<String> {
		return orderRepository.addRiskOrder(param)
	}
}