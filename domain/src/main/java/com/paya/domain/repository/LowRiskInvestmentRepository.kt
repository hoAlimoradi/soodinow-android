package com.paya.domain.repository

import com.paya.domain.models.repo.*
import com.paya.domain.tools.Resource

interface LowRiskInvestmentRepository {
	suspend fun getLowRiskStocks(
		lowRiskStockRequest: LowRiskStockRequest
	): Resource<IsInRiskListRepoModel>
	
	suspend fun exitAccount() : Resource<ExitAccountRepoModel>
	
	suspend fun addRiskOrder(
		addRiskOrderRepoBodyModel: AddRiskOrderRepoBodyModel
	) : Resource<AddRiskOrderRepoModel>

}