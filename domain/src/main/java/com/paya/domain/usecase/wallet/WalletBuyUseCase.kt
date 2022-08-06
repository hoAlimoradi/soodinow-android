package com.paya.domain.usecase.wallet

import com.paya.domain.models.repo.*
import com.paya.domain.repository.LowRiskInvestmentRepository
import com.paya.domain.repository.MarketRepository
import com.paya.domain.repository.WalletRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class WalletBuyUseCase @Inject constructor(
    private val walletRepository: WalletRepository
) : UseCase<WalletBuyRepoBodyModel, WalletBuyRepoModel> {
    override suspend fun action(param: WalletBuyRepoBodyModel): Resource<WalletBuyRepoModel> {
        return walletRepository.buyWallet(param.investmentValue,param.hostId)

    }

}