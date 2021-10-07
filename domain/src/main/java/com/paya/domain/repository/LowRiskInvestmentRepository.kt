package com.paya.domain.repository

import com.paya.domain.models.repo.*
import com.paya.domain.tools.Resource

interface LowRiskInvestmentRepository {
    suspend fun getLowRiskStocks(
        lowRiskStockRequest: LowRiskStockRequest
    ): Resource<IsInRiskListRepoModel>

    suspend fun exitAccount(): Resource<ExitAccountRepoModel>

    suspend fun addRiskOrder(
        addRiskOrderRepoBodyModel: AddRiskOrderRepoBodyModel
    ): Resource<String>

    suspend fun boxTypes(): Resource<BoxTypeRepoModel>

    suspend fun getSellPrice(type: String): Resource<List<Long>>

    suspend fun setPullPrice(type: String, price: Long): Resource<String>

    suspend fun totalBoxValue() : Resource<TotalBoxValueRepoModel>

    suspend fun getHistoryPrice(page: Int, filter: String): Resource<HistoryPriceRepoModel>

    suspend fun getInvestmentLogs(page: Int, pageSize: Int,filters:Map<String,String>): Resource<InvestmentLogsRepoModel>

    suspend fun getChartProfit(boxType:Long) : Resource<List<ChartProfitRepoModel>>

    suspend fun getSoodinowWalletContractRepoModel(): Resource<List<SoodinowWalletContractRepoModel>>

    suspend fun getSoodinowWalletValue(): Resource<SoodinowWalletValueRepoModel>
}