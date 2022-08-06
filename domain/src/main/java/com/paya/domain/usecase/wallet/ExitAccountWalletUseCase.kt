package com.paya.domain.usecase.wallet

import com.paya.domain.models.repo.ExitAccountRepoModel
import com.paya.domain.models.repo.MarketRepoModel
import com.paya.domain.repository.LowRiskInvestmentRepository
import com.paya.domain.repository.MarketRepository
import com.paya.domain.repository.WalletRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class ExitAccountWalletUseCase @Inject constructor(
	private val walletRepository: WalletRepository
): UseCase<String, ExitAccountRepoModel> {
	override suspend fun action(param: String): Resource<ExitAccountRepoModel> {
		return walletRepository.exitAccount()
	}
}