package com.paya.domain.usecase.order

import com.paya.domain.models.repo.AddRiskOrderRepoBodyModel
import com.paya.domain.models.repo.AddRiskOrderRepoModel
import com.paya.domain.models.repo.ExitAccountRepoModel
import com.paya.domain.models.repo.MarketRepoModel
import com.paya.domain.repository.LowRiskInvestmentRepository
import com.paya.domain.repository.MarketRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class AddRiskOrderUseCase @Inject constructor(
	private val orderRepository: LowRiskInvestmentRepository
): UseCase<AddRiskOrderRepoBodyModel, AddRiskOrderRepoModel> {
	override suspend fun action(param: AddRiskOrderRepoBodyModel): Resource<AddRiskOrderRepoModel> {
		return orderRepository.addRiskOrder(param)
	}
}