package com.paya.domain.repository


import com.paya.domain.models.repo.*
import com.paya.domain.tools.Resource

interface WalletRepository {

    suspend fun buyWallet(
        investmentValue: Long,
        hostId: Int
    ): Resource<WalletBuyRepoModel>

    suspend fun walletCharge(
        charge: Long,
        callbackUrl: String,
        bankingPortal: String
    ): Resource<WalletChargeRepoModel>

    suspend fun preWithdraw(
        id: Int
    ): Resource<WalletPreWithdrawRepoModel>

    suspend fun withdrawRequest(
        id: Int
    ): Resource<WalletWithdrawRequestRepoModel>

    suspend fun portfolio(): Resource<WalletPortfolioRepoModel>

    suspend fun wallet(): Resource<WalletValueRepoModel>

}