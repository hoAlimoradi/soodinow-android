package com.paya.domain.usecase.order

import com.paya.domain.models.repo.ExitAccountRepoModel
import com.paya.domain.models.repo.MarketRepoModel
import com.paya.domain.repository.LowRiskInvestmentRepository
import com.paya.domain.repository.MarketRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class ExitAccountUseCase @Inject constructor(
	private val orderRepository: LowRiskInvestmentRepository
): UseCase<Unit, ExitAccountRepoModel> {
	override suspend fun action(param: Unit): Resource<ExitAccountRepoModel> {
		return orderRepository.exitAccount()
	}
}