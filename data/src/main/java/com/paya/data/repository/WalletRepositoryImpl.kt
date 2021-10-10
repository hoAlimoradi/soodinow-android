package com.paya.data.repository

import com.paya.common.Mapper
import com.paya.data.network.remote_api.WalletService
import com.paya.data.utils.getResourceFromApiResponse
import com.paya.domain.models.remote.*
import com.paya.domain.models.repo.*
import com.paya.domain.repository.WalletRepository
import com.paya.domain.tools.Resource
import javax.inject.Inject

class WalletRepositoryImpl @Inject constructor(
    private val walletService: WalletService,
    private val walletBuyRemoteRepoMapper: Mapper<String, WalletBuyRepoModel>,
    private val walletChargeRemoteRepoMapper: Mapper<WalletChargeRemoteModel, WalletChargeRepoModel>,
    private val walletPreWithdrawRemoteRepoMapper: Mapper<WalletPreWithdrawRemoteModel, WalletPreWithdrawRepoModel>,
    private val walletWithdrawRequestRemoteRepoMapper: Mapper<WalletWithdrawRequestRemoteModel, WalletWithdrawRequestRepoModel>,
    private val walletPortfolioRemoteRepoMapper: Mapper<WalletPortfolioRemoteModel, WalletPortfolioRepoModel>,
    private val walletValueRemoteRepoMapper: Mapper<WalletValueRemoteModel, WalletValueRepoModel>,
    private val investingInfoRemoteRepoMapper: Mapper<InvestingInfoRemoteModel, InvestingInfoRepoModel>,
    private val walletHostListRemoteRepoMapper: Mapper<@JvmSuppressWildcards List<WalletHostListRemoteModel>, @JvmSuppressWildcards List<WalletHostListRepoModel>> ,
    private val bankPortalRemoteRepoMapper: Mapper<@JvmSuppressWildcards List<PortalBankRemoteModel>, @JvmSuppressWildcards List<PortalBankRepoModel>> ,
) : WalletRepository {
    override suspend fun buyWallet(
        investmentValue: Long,
        hostId: Int
    ): Resource<WalletBuyRepoModel> {
        return getResourceFromApiResponse(
            walletService.buyWallet(investmentValue, hostId)
        ) {
            walletBuyRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun walletCharge(
        charge: Long,
        callbackUrl: String,
        bankingPortal: String
    ): Resource<WalletChargeRepoModel> {
        return getResourceFromApiResponse(
            walletService.walletCharge(charge, callbackUrl, bankingPortal)
        ) {
            walletChargeRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun preWithdraw(id: Int): Resource<WalletPreWithdrawRepoModel> {
        return getResourceFromApiResponse(
            walletService.preWithdraw(id)
        ) {
            walletPreWithdrawRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun withdrawRequest(id: Int,sell:Long): Resource<WalletWithdrawRequestRepoModel> {
        return getResourceFromApiResponse(
            walletService.withdrawRequest(id,sell)
        ) {
            walletWithdrawRequestRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun portfolio(): Resource<WalletPortfolioRepoModel> {
        return getResourceFromApiResponse(
            walletService.portfolio()
        ) {
            walletPortfolioRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun wallet(): Resource<WalletValueRepoModel> {
        return getResourceFromApiResponse(
            walletService.wallet()
        ) {
            walletValueRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun hostList(): Resource<List<WalletHostListRepoModel>> {
        return getResourceFromApiResponse(
            walletService.hostList()
        ) {
            walletHostListRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun investingInfo(): Resource<InvestingInfoRepoModel> {
        return getResourceFromApiResponse(
            walletService.investingInfo()
        ) {
            investingInfoRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun bankPortals(): Resource<List<PortalBankRepoModel>> {
        return getResourceFromApiResponse(walletService.bankPortals()) {
            bankPortalRemoteRepoMapper.map(it.data)
        }
    }
}