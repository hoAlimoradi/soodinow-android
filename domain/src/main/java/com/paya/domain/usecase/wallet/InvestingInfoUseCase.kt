package com.paya.domain.usecase.wallet

import com.paya.domain.models.repo.*
import com.paya.domain.repository.LowRiskInvestmentRepository
import com.paya.domain.repository.MarketRepository
import com.paya.domain.repository.WalletRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class InvestingInfoUseCase @Inject constructor(
    private val walletRepository: WalletRepository
) : UseCase<Unit, InvestingInfoRepoModel> {
    override suspend fun action(param: Unit): Resource<InvestingInfoRepoModel> {
        return walletRepository.investingInfo()
    }
}