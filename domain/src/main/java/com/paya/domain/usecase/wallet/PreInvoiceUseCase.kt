package com.paya.domain.usecase.wallet

import com.paya.domain.models.repo.PortalBankRepoModel
import com.paya.domain.models.repo.PreInvoiceBodyRepoModel
import com.paya.domain.models.repo.PreInvoiceRepoModel
import com.paya.domain.repository.WalletRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class PreInvoiceUseCase @Inject constructor(
    private val walletRepository: WalletRepository
) : UseCase<PreInvoiceBodyRepoModel, PreInvoiceRepoModel> {
    override suspend fun action(param: PreInvoiceBodyRepoModel): Resource<PreInvoiceRepoModel> {
        return walletRepository.preInvoice(param.hostId,param.price)
    }

}