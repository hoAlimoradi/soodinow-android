package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.IsInRiskListRemoteModel
import com.paya.domain.models.repo.BasketRepoModel
import com.paya.domain.models.repo.IsInRiskListRepoModel
import com.paya.domain.models.repo.Percent
import com.paya.domain.models.repo.PercentRepoModel
import javax.inject.Inject

class IsInRiskListRemoteRepoMapper @Inject constructor(): Mapper<
		IsInRiskListRemoteModel,
		IsInRiskListRepoModel>{
	
	override fun map(param: IsInRiskListRemoteModel): IsInRiskListRepoModel {
		return IsInRiskListRepoModel(
			param.basket.map {
				BasketRepoModel(
					it.name,
					it.namad,
					it.percent
				)
			},
			PercentRepoModel(
				minimum = Percent(param.percent.minimum.percent, param.percent.minimum.price),
				year = Percent(param.percent.year.percent, param.percent.year.price),
				perfect = Percent(param.percent.perfect.percent, param.percent.perfect.price)
			)
		)
	}
	
}