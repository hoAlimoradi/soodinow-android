package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.BoxHistoryRemoteModel
import com.paya.domain.models.remote.HistoryPriceRemoteModel
import com.paya.domain.models.repo.*
import javax.inject.Inject

class FarabiAuthRemoteRepoMapper @Inject constructor(): Mapper<
		String,
		FarabiTokenRepoModel>{
	
	override fun map(param: String): FarabiTokenRepoModel {

		return FarabiTokenRepoModel(param)
	}
	
}