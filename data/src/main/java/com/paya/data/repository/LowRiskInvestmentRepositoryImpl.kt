package com.paya.data.repository

import com.paya.common.Mapper
import com.paya.data.mapper.HistoryPriceRemoteRepoMapper
import com.paya.data.network.remote_api.LowRiskInvestmentService
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
    private val addRiskOrderRemoteRepoMapper: Mapper<String,String>,
    private val addRiskOrderRepoRemoteMapper: Mapper<AddRiskOrderRepoBodyModel, AddRiskOrderRemoteBodyModel>,
    private val boxTypesRemoteRepoMapper: Mapper<BoxTypeRemoteModel, BoxTypeRepoModel>,
    private val getSellPriceRemoteRepoMapper: Mapper<@JvmSuppressWildcards List<List<Long>>, @JvmSuppressWildcards List<Long>>,
    private val pullPriceRemoteRepoMapper: Mapper<String, String>,
    private val totalBoxValueRemoteRepoMapper: Mapper<TotalBoxValueRemoteModel, TotalBoxValueRepoModel>,
    private val historyPriceRemoteRepoMapper: Mapper<HistoryPriceRemoteModel, HistoryPriceRepoModel>
) : LowRiskInvestmentRepository {

    override suspend fun getLowRiskStocks(lowRiskStockRequest: LowRiskStockRequest): Resource<IsInRiskListRepoModel> {
        return getResourceFromApiResponse(
            lowRiskInvestmentService.getLowRiskInvestment(
                lowRiskStockRequest.price,
                lowRiskStockRequest.type
            )
        ) {
            isInRiskRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun exitAccount(): Resource<ExitAccountRepoModel> {
        return getResourceFromApiResponse(lowRiskInvestmentService.exitAccount()) {
            exitAccountRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun addRiskOrder(addRiskOrderRepoBodyModel: AddRiskOrderRepoBodyModel): Resource<String> {
        return getResourceFromApiResponse(
            lowRiskInvestmentService.addRiskOrder(
                addRiskOrderRepoRemoteMapper.map(addRiskOrderRepoBodyModel)
            )
        ) {
             addRiskOrderRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun boxTypes(): Resource<BoxTypeRepoModel> {
        return getResourceFromApiResponse(lowRiskInvestmentService.boxTypes()) {
            boxTypesRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun getSellPrice(type: String): Resource<List<Long>> {
        return getResourceFromApiResponse(
            lowRiskInvestmentService.getSellPrice(type)
        ) {
            getSellPriceRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun setPullPrice(type: String, price: Long): Resource<String> {
        return getResourceFromApiResponse(
            lowRiskInvestmentService.setPullPrice(type, price)
        ) {
            pullPriceRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun totalBoxValue(): Resource<TotalBoxValueRepoModel> {
        return getResourceFromApiResponse(
            lowRiskInvestmentService.totalBoxValue()
        ) {
            totalBoxValueRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun getHistoryPrice(
        page: Int,
        filter: String
    ): Resource<HistoryPriceRepoModel> {
        return getResourceFromApiResponse(
            lowRiskInvestmentService.getHistoryPrice(page, filter)
        ) {
            historyPriceRemoteRepoMapper.map(it.data)
        }
    }

}