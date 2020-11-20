package com.paya.data.repository

import com.paya.common.Mapper
import com.paya.data.network.remote_api.LowRiskInvestmentService
import com.paya.data.utils.getResourceFromApiResponse
import com.paya.domain.models.remote.IsInRiskListRemoteModel
import com.paya.domain.models.repo.IsInRiskListRepoModel
import com.paya.domain.models.repo.LowRiskStockRequest
import com.paya.domain.repository.LowRiskInvestmentRepository
import com.paya.domain.tools.Resource
import javax.inject.Inject

data class LowRiskInvestmentRepositoryImpl @Inject constructor(
	private val lowRiskInvestmentService: LowRiskInvestmentService,
	private val isInRiskRemoteRepoMapper: Mapper<IsInRiskListRemoteModel, IsInRiskListRepoModel>
): LowRiskInvestmentRepository{
	
	override suspend fun getLowRiskStocks(lowRiskStockRequest: LowRiskStockRequest): Resource<IsInRiskListRepoModel> {
		return getResourceFromApiResponse(
			lowRiskInvestmentService.getLowRiskInvestment(lowRiskStockRequest.price, lowRiskStockRequest.type)
		){
			isInRiskRemoteRepoMapper.map(it.data)
		}
	}
	
}