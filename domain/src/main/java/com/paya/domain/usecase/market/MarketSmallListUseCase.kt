package com.paya.domain.usecase.market

import com.paya.domain.models.repo.MarketRepoModel
import com.paya.domain.repository.MarketRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class MarketSmallListUseCase @Inject constructor(
	private val marketRepository: MarketRepository
): UseCase<Unit, MarketRepoModel> {
	override suspend fun action(param: Unit): Resource<MarketRepoModel> {
		return marketRepository.getSmallListMarket()
	}
}