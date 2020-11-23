package com.paya.domain.usecase.order

import com.paya.domain.models.repo.*
import com.paya.domain.repository.LowRiskInvestmentRepository
import com.paya.domain.repository.MarketRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class AddRiskOrderUseCase @Inject constructor(
	private val orderRepository: LowRiskInvestmentRepository
): UseCase<AddRiskOrderRepoBodyModel, List<AddRiskOrderRepoItem>> {
	override suspend fun action(param: AddRiskOrderRepoBodyModel): Resource<List<AddRiskOrderRepoItem>> {
		return orderRepository.addRiskOrder(param)
	}
}