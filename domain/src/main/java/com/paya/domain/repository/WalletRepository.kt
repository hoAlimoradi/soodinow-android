package com.paya.domain.repository


import com.paya.domain.models.remote.PreInvoiceRemoteModel
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
        id: Int,
        sell: Long
    ): Resource<WalletWithdrawRequestRepoModel>

    suspend fun portfolio(): Resource<WalletPortfolioRepoModel>

    suspend fun wallet(): Resource<WalletValueRepoModel>
    suspend fun cashWithdrawRequest(amount: Long): Resource<CashWithdrawRequestRepoModel>

    suspend fun hostList(): Resource<List<WalletHostListRepoModel>>
    suspend fun hostDetail(id:Int): Resource<WalletHostDetailRepoModel>
    suspend fun investingInfo(): Resource<InvestingInfoRepoModel>
    suspend fun bankPortals(): Resource<List<PortalBankRepoModel>>
    suspend fun getPreInvoice(): Resource<PreInvoiceRepoModel>
    suspend fun preInvoice(hostId:Int,price:Long): Resource<PreInvoiceRepoModel>

    suspend fun getAddInventoryPriceList(): Resource<List<AddInventoryPriceRepoModel>>
    suspend fun exitAccount(): Resource<ExitAccountRepoModel>
}