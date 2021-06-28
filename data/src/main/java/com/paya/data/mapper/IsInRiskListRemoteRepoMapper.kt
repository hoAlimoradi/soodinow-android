package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.IsInRiskListRemoteModel
import com.paya.domain.models.repo.BasketRepoModel
import com.paya.domain.models.repo.IsInRiskListRepoModel
import com.paya.domain.models.repo.PercentEfficiency
import javax.inject.Inject

class IsInRiskListRemoteRepoMapper @Inject constructor(): Mapper<
		IsInRiskListRemoteModel,
		IsInRiskListRepoModel>{
	
	override fun map(param: IsInRiskListRemoteModel): IsInRiskListRepoModel {


		return IsInRiskListRepoModel(
			param.basket?.let {
				it.map { item ->
					item.percent?.let { percent -> percent * 100 } ?: 0f
				}
			} ?: emptyList(),
			param.basket?.let {
				it.map { item ->
					BasketRepoModel(
						item.name ?: "",
						item.namad ?: "",
						item.percent?.let { percent -> percent * 100 } ?: 0f,
						item.quantity ?: 0f,
						item.color ?: "#3bb400"
					)
				}
			} ?: emptyList(),
			param.soodinowProfit?.map {
				PercentEfficiency(it.name, it.profit)
			} ?: emptyList()
		)
	}
	
}