package com.paya.domain.usecase.wallet

import com.paya.domain.models.repo.PortalBankRepoModel
import com.paya.domain.repository.WalletRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class BankPortalUseCase @Inject constructor(
    private val walletRepository: WalletRepository
) : UseCase<Unit, @JvmSuppressWildcards List<PortalBankRepoModel>> {
    override suspend fun action(param: Unit): Resource<List<PortalBankRepoModel>> {
        return walletRepository.bankPortals()
    }

}