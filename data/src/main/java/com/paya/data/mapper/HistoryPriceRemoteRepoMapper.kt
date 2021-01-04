package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.BoxHistoryRemoteModel
import com.paya.domain.models.remote.HistoryPriceRemoteModel
import com.paya.domain.models.repo.*
import javax.inject.Inject

class HistoryPriceRemoteRepoMapper @Inject constructor(): Mapper<
		HistoryPriceRemoteModel,
		HistoryPriceRepoModel>{
	
	override fun map(param: HistoryPriceRemoteModel): HistoryPriceRepoModel {

		return HistoryPriceRepoModel(
			1,
			param.priceHistory.map {
				PriceModel(it.title)
			}
		)
	}
	
}