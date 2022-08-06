package com.paya.domain.usecase.wallet

import com.paya.domain.models.repo.*
import com.paya.domain.repository.LowRiskInvestmentRepository
import com.paya.domain.repository.MarketRepository
import com.paya.domain.repository.WalletRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class WalletHostListUseCase @Inject constructor(
    private val walletRepository: WalletRepository
) : UseCase<Unit, @JvmSuppressWildcards List<WalletHostListRepoModel>> {
    override suspend fun action(param: Unit): Resource<List<WalletHostListRepoModel>> {
        return walletRepository.hostList()

    }

}