package com.paya.domain.repository

import com.paya.domain.models.repo.BoxHistoryRepoModel
import com.paya.domain.models.repo.BoxHistoryRequestModel
import com.paya.domain.tools.Resource

interface BoxHistoryRepository {
	suspend fun getBoxHistory(boxHistoryRequestModel: BoxHistoryRequestModel): Resource<BoxHistoryRepoModel>
}