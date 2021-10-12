package com.paya.domain.usecase.wallet

import com.paya.domain.models.repo.PortalBankRepoModel
import com.paya.domain.models.repo.PreInvoiceRepoModel
import com.paya.domain.repository.WalletRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class GetPreInvoiceUseCase @Inject constructor(
    private val walletRepository: WalletRepository
) : UseCase<Unit, @JvmSuppressWildcards PreInvoiceRepoModel> {
    override suspend fun action(param: Unit): Resource<PreInvoiceRepoModel> {
        return walletRepository.getPreInvoice()
    }

}