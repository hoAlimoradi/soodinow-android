package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.MarketRemoteModel
import com.paya.domain.models.repo.MarketRepoModel
import javax.inject.Inject

class MarketRemoteRepoMapper @Inject constructor() : Mapper<
		MarketRemoteModel,
		MarketRepoModel
		> {
	
	override fun map(param: MarketRemoteModel): MarketRepoModel {
		return MarketRepoModel(param.number ?: "")
	}
	
}