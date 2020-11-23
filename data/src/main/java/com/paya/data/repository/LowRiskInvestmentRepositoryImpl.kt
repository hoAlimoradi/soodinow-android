package com.paya.data.repository

import com.paya.common.Mapper
import com.paya.data.network.remote_api.LowRiskInvestmentService
import com.paya.data.utils.getResourceFromApiResponse
import com.paya.domain.models.remote.AddRiskOrderRemoteBodyModel
import com.paya.domain.models.remote.AddRiskOrderRemoteModel
import com.paya.domain.models.remote.ExitAccountRemoteModel
import com.paya.domain.models.remote.IsInRiskListRemoteModel
import com.paya.domain.models.repo.*
import com.paya.domain.repository.LowRiskInvestmentRepository
import com.paya.domain.tools.Resource
import javax.inject.Inject

data class LowRiskInvestmentRepositoryImpl @Inject constructor(
	private val lowRiskInvestmentService: LowRiskInvestmentService,
	private val isInRiskRemoteRepoMapper: Mapper<IsInRiskListRemoteModel,IsInRiskListRepoModel>,
	private val exitAccountRemoteRepoMapper: Mapper<ExitAccountRemoteModel,ExitAccountRepoModel>,
	private val addRiskOrderRemoteRepoMapper: Mapper<AddRiskOrderRemoteModel,AddRiskOrderRepoModel>,
	private val addRiskOrderRepoRemoteMapper: Mapper<AddRiskOrderRepoBodyModel,AddRiskOrderRemoteBodyModel>
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
	
	override suspend fun addRiskOrder(addRiskOrderRepoBodyModel: AddRiskOrderRepoBodyModel): Resource<AddRiskOrderRepoModel> {
		return getResourceFromApiResponse(
			lowRiskInvestmentService.addRiskOrder(
				addRiskOrderRepoRemoteMapper.map(addRiskOrderRepoBodyModel)
			)
		) {
			addRiskOrderRemoteRepoMapper.map(it.data)
		}
	}
	
}