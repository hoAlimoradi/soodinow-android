package com.paya.domain.usecase.wallet

import com.paya.domain.models.repo.AddInventoryPriceRepoModel
import com.paya.domain.repository.WalletRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class WalletChargeAddInventoryPriceListUseCase @Inject constructor(
    private val walletRepository: WalletRepository
) : UseCase<Unit,  @JvmSuppressWildcards List<AddInventoryPriceRepoModel>> {
    override suspend fun action(param: Unit): Resource<List<AddInventoryPriceRepoModel>> {
        return walletRepository.getAddInventoryPriceList()
    }
}
