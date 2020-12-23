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
    ): Resource<List<AddRiskOrderRepoItem>>

    suspend fun boxTypes(): Resource<BoxTypeRepoModel>

    suspend fun getSellPrice(type: String): Resource<List<Long>>

    suspend fun setPullPrice(type: String, price: Long): Resource<Unit>
}