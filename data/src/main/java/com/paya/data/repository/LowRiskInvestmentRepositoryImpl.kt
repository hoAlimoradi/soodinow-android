package com.paya.data.repository

import com.paya.common.Mapper
import com.paya.data.network.remote_api.LowRiskInvestmentService
import com.paya.data.sharedpreferences.PreferenceHelper
import com.paya.data.utils.getResourceFromApiResponse
import com.paya.domain.models.remote.*
import com.paya.domain.models.repo.*
import com.paya.domain.repository.LowRiskInvestmentRepository
import com.paya.domain.tools.Resource
import javax.inject.Inject

data class LowRiskInvestmentRepositoryImpl @Inject constructor(
    private val lowRiskInvestmentService: LowRiskInvestmentService,
    private val isInRiskRemoteRepoMapper: Mapper<IsInRiskListRemoteModel, IsInRiskListRepoModel>,
    private val exitAccountRemoteRepoMapper: Mapper<ExitAccountRemoteModel, ExitAccountRepoModel>,
    private val addRiskOrderRemoteRepoMapper: Mapper<String, String>,
    private val addRiskOrderRepoRemoteMapper: Mapper<AddRiskOrderRepoBodyModel, AddRiskOrderRemoteBodyModel>,
    private val boxTypesRemoteRepoMapper: Mapper<BoxTypeRemoteModel, BoxTypeRepoModel>,
    private val getSellPriceRemoteRepoMapper: Mapper<@JvmSuppressWildcards List<List<Float>>, @JvmSuppressWildcards List<Long>>,
    private val pullPriceRemoteRepoMapper: Mapper<String, String>,
    private val totalBoxValueRemoteRepoMapper: Mapper<TotalBoxValueRemoteModel, TotalBoxValueRepoModel>,
    private val historyPriceRemoteRepoMapper: Mapper<HistoryPriceRemoteModel, HistoryPriceRepoModel>,
    private val investmentLogsRemoteRepoMapper: Mapper<InvestmentLogsRemoteModel, InvestmentLogsRepoModel>,
    private val chartProfitRemoteRepo: Mapper<@JvmSuppressWildcards List<ChartProfitRemoteModel>, @JvmSuppressWildcards List<ChartProfitRepoModel>>,
    private val preferenceHelper: PreferenceHelper,
) : LowRiskInvestmentRepository {

    override suspend fun getLowRiskStocks(lowRiskStockRequest: LowRiskStockRequest): Resource<IsInRiskListRepoModel> {
        return getResourceFromApiResponse(
            lowRiskInvestmentService.getLowRiskInvestment(
                preferenceHelper.getAccessToken(),
                lowRiskStockRequest.price,
                lowRiskStockRequest.type
            )
        ) {
            isInRiskRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun exitAccount(): Resource<ExitAccountRepoModel> {
        return getResourceFromApiResponse(lowRiskInvestmentService.exitAccount(preferenceHelper.getAccessToken())) {
            exitAccountRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun addRiskOrder(addRiskOrderRepoBodyModel: AddRiskOrderRepoBodyModel): Resource<String> {
        return getResourceFromApiResponse(
            lowRiskInvestmentService.addRiskOrder(
                preferenceHelper.getAccessToken(),
                addRiskOrderRepoRemoteMapper.map(addRiskOrderRepoBodyModel)
            )
        ) {
            addRiskOrderRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun boxTypes(): Resource<BoxTypeRepoModel> {
        return getResourceFromApiResponse(lowRiskInvestmentService.boxTypes(preferenceHelper.getAccessToken())) {
            boxTypesRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun getSellPrice(type: String): Resource<List<Long>> {
        return getResourceFromApiResponse(
            lowRiskInvestmentService.getSellPrice(preferenceHelper.getAccessToken(), type)
        ) {
            getSellPriceRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun setPullPrice(type: String, price: Long): Resource<String> {
        return getResourceFromApiResponse(
            lowRiskInvestmentService.setPullPrice(preferenceHelper.getAccessToken(), type, price)
        ) {
            pullPriceRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun totalBoxValue(): Resource<TotalBoxValueRepoModel> {
        return getResourceFromApiResponse(
            lowRiskInvestmentService.totalBoxValue(preferenceHelper.getAccessToken())
        ) {
            totalBoxValueRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun getHistoryPrice(
        page: Int,
        filter: String
    ): Resource<HistoryPriceRepoModel> {
        return getResourceFromApiResponse(
            lowRiskInvestmentService.getHistoryPrice(
                preferenceHelper.getAccessToken(),
                page,
                filter
            )
        ) {
            historyPriceRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun getInvestmentLogs(
        page: Int,
        pageSize: Int,
        filters: Map<String, String>
    ): Resource<InvestmentLogsRepoModel> {
        return getResourceFromApiResponse(
            lowRiskInvestmentService.getInvestmentLogs(
                preferenceHelper.getAccessToken(),
                page,
                pageSize,
                filters
            )
        ) {
            investmentLogsRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun getChartProfit(boxType: Long): Resource<List<ChartProfitRepoModel>> {
        return getResourceFromApiResponse(
            lowRiskInvestmentService.getChartProfit(
                preferenceHelper.getAccessToken(),
                boxType
            )
        ) {
            chartProfitRemoteRepo.map(it.data)
        }
    }

    override suspend fun getSoodinowWalletContractRepoModel(): Resource<List<SoodinowWalletContractRepoModel>> {
        val soodinowWalletContractRepoModel = SoodinowWalletContractRepoModel(
            pointTitle = "IRT1AFRN0001",
            name = "افران",
            description = "افران",
            trimesterValue =  25,
            monthlyValue =  25,
            weeklyValue =  25
        )
        return Resource.success(listOf(soodinowWalletContractRepoModel) , 200)
    }

    override suspend fun getSoodinowWalletValue(): Resource<SoodinowWalletValueRepoModel> {

        val soodinowWalletValuePercentFirst = SoodinowWalletValuePercent(
            isin = "IRT1AFRN0001",
            namad = "افران",
            percent = 0.25
        )

        val soodinowWalletValuePercentSecond = SoodinowWalletValuePercent(
            isin = "IRT3FKOF0001",
            namad = "اکورد",
            percent = 0.5
        )

        val soodinowWalletValuePercentThird = SoodinowWalletValuePercent(
            isin = "IRT1YGHT0001",
            namad = "یاقوت",
            percent = 0.25
        )

        val soodinowWalletValueRepoModel = SoodinowWalletValueRepoModel(
            id = 18,
            value = 961481259240313.0,
            percent = listOf(
                soodinowWalletValuePercentFirst,
                soodinowWalletValuePercentSecond,
                soodinowWalletValuePercentThird
            )
        )
        return Resource.success(soodinowWalletValueRepoModel , 200)

    }

}