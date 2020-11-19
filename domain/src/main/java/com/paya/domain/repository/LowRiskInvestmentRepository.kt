package com.paya.domain.repository

import com.paya.domain.models.repo.IsInRiskListRepoModel
import com.paya.domain.models.repo.LowRiskStockRequest
import com.paya.domain.tools.Resource

interface LowRiskInvestmentRepository {
	suspend fun getLowRiskStocks(
		lowRiskStockRequest: LowRiskStockRequest
	): Resource<IsInRiskListRepoModel>
}