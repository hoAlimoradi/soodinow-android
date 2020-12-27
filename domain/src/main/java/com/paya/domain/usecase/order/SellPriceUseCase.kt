package com.paya.domain.usecase.order

import com.paya.domain.models.repo.BoxTypeRepoModel
import com.paya.domain.models.repo.ExitAccountRepoModel
import com.paya.domain.models.repo.MarketRepoModel
import com.paya.domain.repository.LowRiskInvestmentRepository
import com.paya.domain.repository.MarketRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class SellPriceUseCase @Inject constructor(
	private val orderRepository: LowRiskInvestmentRepository
): UseCase<String, @JvmSuppressWildcards List<Long>> {
	override suspend fun action(param: String): Resource<List<Long>> {
		return orderRepository.getSellPrice(param)
	}
}