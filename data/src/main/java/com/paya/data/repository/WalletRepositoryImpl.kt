package com.paya.data.repository

import android.util.Log
import com.paya.common.Mapper
import com.paya.data.mapper.CashWithdrawRequestRemoteRepoMapper
import com.paya.data.network.apiresponse.ApiSuccessResponse
import com.paya.data.network.remote_api.WalletService
import com.paya.data.sharedpreferences.PreferenceHelper
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
    private val cashWithdrawRequestRemoteRepoMapper: Mapper<String,CashWithdrawRequestRepoModel>,
    private val preInvoiceRemoteRepoMapper: Mapper<PreInvoiceRemoteModel,PreInvoiceRepoModel>,
    private val walletHostListRemoteRepoMapper: Mapper<@JvmSuppressWildcards List<WalletHostListRemoteModel>, @JvmSuppressWildcards List<WalletHostListRepoModel>>,
    private val walletHostDetailRemoteRepoMapper: Mapper<WalletHostDetailRemoteModel, WalletHostDetailRepoModel>,
    private val bankPortalRemoteRepoMapper: Mapper<@JvmSuppressWildcards List<PortalBankRemoteModel>, @JvmSuppressWildcards List<PortalBankRepoModel>>,
    private val preferenceHelper: PreferenceHelper,
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

    override suspend fun cashWithdrawRequest(amount: Long): Resource<CashWithdrawRequestRepoModel> {
        return getResourceFromApiResponse(
            walletService.cashWithdrawRequest(amount)
        ) {
            cashWithdrawRequestRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun hostList(): Resource<List<WalletHostListRepoModel>> {
        try{
            walletService.hostList()
        } catch (e:Exception) {
            Log.e("sdklsdjksdksa",e.message,e)
        }
        return getResourceFromApiResponse(
            walletService.hostList()
        ) {
            walletHostListRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun hostDetail(id: Int): Resource<WalletHostDetailRepoModel> {
        return getResourceFromApiResponse(
            walletService.hostDetail(id)
        ) {
            walletHostDetailRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun investingInfo(): Resource<InvestingInfoRepoModel> {
        return getResourceFromApiResponse(
            walletService.investingInfo()
        ) {
            investingInfoRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun getAddInventoryPriceList(): Resource<List<AddInventoryPriceRepoModel>> {
        val first = AddInventoryPriceRepoModel(name = "100 تومان", price = 100F)
        val second = AddInventoryPriceRepoModel(name = "250 تومان", price = 100F)
        val third = AddInventoryPriceRepoModel(name = "500 تومان", price = 100F)
        val fourth = AddInventoryPriceRepoModel(name = "100 تومان", price = 100F)
        val fifth = AddInventoryPriceRepoModel(name = "100 تومان", price = 100F)
        val sixth = AddInventoryPriceRepoModel(name = "100 تومان", price = 100F)
        val list = listOf<AddInventoryPriceRepoModel>(first, second, third, fourth, fifth, sixth)
        return Resource.success(list, 200)
    }

    override suspend fun bankPortals(): Resource<List<PortalBankRepoModel>> {
        return getResourceFromApiResponse(walletService.bankPortals()) {
            bankPortalRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun getPreInvoice(): Resource<PreInvoiceRepoModel> {
        return getResourceFromApiResponse(walletService.getPreInvoice(preferenceHelper.getPreInvoiceId())) {
            preInvoiceRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun preInvoice(hostId:Int,price: Long): Resource<PreInvoiceRepoModel> {
        val preInvoice = walletService.preInvoice(hostId,price)
        if (preInvoice is ApiSuccessResponse) {
            preInvoice.body.data.uuid?.let { preferenceHelper.setPreInvoiceId(it) }
        }
        return getResourceFromApiResponse(preInvoice) {
            preInvoiceRemoteRepoMapper.map(it.data)
        }
    }
}