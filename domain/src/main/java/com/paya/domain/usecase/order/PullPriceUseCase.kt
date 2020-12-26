package com.paya.domain.usecase.order

import com.paya.domain.models.repo.BoxTypeRepoModel
import com.paya.domain.models.repo.ExitAccountRepoModel
import com.paya.domain.models.repo.MarketRepoModel
import com.paya.domain.models.repo.PullPriceBodyRepoModel
import com.paya.domain.repository.LowRiskInvestmentRepository
import com.paya.domain.repository.MarketRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class PullPriceUseCase @Inject constructor(
	private val orderRepository: LowRiskInvestmentRepository
): UseCase<PullPriceBodyRepoModel, String> {
	override suspend fun action(param: PullPriceBodyRepoModel): Resource<String> {
		return orderRepository.setPullPrice(param.type,param.price)
	}
}