package com.paya.domain.usecase.price

import com.paya.domain.models.repo.*
import com.paya.domain.repository.LowRiskInvestmentRepository
import com.paya.domain.repository.MarketRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class HistoryPriceUseCase @Inject constructor(
    private val orderRepository: LowRiskInvestmentRepository
) : UseCase<HistoryPriceBodyRepoModel, HistoryPriceRepoModel> {
    override suspend fun action(param: HistoryPriceBodyRepoModel): Resource<HistoryPriceRepoModel> {
        return orderRepository.getHistoryPrice(param.page,param.filter)

    }

}