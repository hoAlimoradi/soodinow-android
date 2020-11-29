package com.paya.data.repository

import com.paya.common.Mapper
import com.paya.data.mapper.BoxHistoryRemoteRepoMapper
import com.paya.data.network.remote_api.BoxHistoryService
import com.paya.data.utils.getResourceFromApiResponse
import com.paya.domain.models.remote.BoxHistoryRemoteModel
import com.paya.domain.models.repo.BoxHistoryRepoModel
import com.paya.domain.models.repo.BoxHistoryRequestModel
import com.paya.domain.repository.BoxHistoryRepository
import com.paya.domain.tools.Resource
import javax.inject.Inject

class BoxHistoryRepositoryImpl @Inject constructor(
	private val boxHistoryService: BoxHistoryService,
	private val boxHistoryRemoteRepoMapper: Mapper<BoxHistoryRemoteModel,BoxHistoryRepoModel>
): BoxHistoryRepository{
	
	override suspend fun getBoxHistory(boxHistoryRequestModel: BoxHistoryRequestModel): Resource<BoxHistoryRepoModel> {
		val boxHistoryResponse = boxHistoryService.getBoxHistory(
			boxId = boxHistoryRequestModel.boxId,
			type = boxHistoryRequestModel.type,
			number = boxHistoryRequestModel.number
		)
		return getResourceFromApiResponse(boxHistoryResponse){
			boxHistoryRemoteRepoMapper.map(it.data)
		}
	}
	
}