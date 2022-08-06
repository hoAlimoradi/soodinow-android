package com.paya.domain.usecase.boxHistory

import com.paya.domain.models.repo.BoxHistoryRepoModel
import com.paya.domain.models.repo.BoxHistoryRequestModel
import com.paya.domain.repository.BoxHistoryRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class GetBoxHistoryUseCase @Inject constructor(
	private val boxHistoryRepository: BoxHistoryRepository
): UseCase<BoxHistoryRequestModel, BoxHistoryRepoModel>{
	
	override suspend fun action(param: BoxHistoryRequestModel): Resource<BoxHistoryRepoModel> {
		return boxHistoryRepository.getBoxHistory(param)
	}
	
}