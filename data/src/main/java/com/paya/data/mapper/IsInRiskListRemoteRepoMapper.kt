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
		val percentEfficiencyList = mutableListOf<PercentEfficiency>()
		param.percent?.let { percent ->
			percent.threeMonth?.let { threeMonth ->
				percentEfficiencyList.add(
					PercentEfficiency(
						"بازدهی 3 ماهه",
						threeMonth.percent?.let { it * 100 } ?: 0f,
						threeMonth.price ?: 0f
					)
				)
			}
			percent.oneMonth?.let { oneMonth ->
				percentEfficiencyList.add(
					PercentEfficiency(
						"بازدهی ماهانه",
						oneMonth.percent?.let { it * 100 } ?: 0f,
						oneMonth.price ?: 0f
					)
				)
			}
			percent.oneWeek?.let { oneWeek ->
				percentEfficiencyList.add(
					PercentEfficiency(
						"بازدهی هفتگی",
						oneWeek.percent?.let { it * 100 } ?: 0f,
						oneWeek.price ?: 0f
					)
				)
			}
		}
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
			percentEfficiencyList
		)
	}
	
}