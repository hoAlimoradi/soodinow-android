package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.BoxHistoryRemoteModel
import com.paya.domain.models.repo.BoxHistoryRepoModel
import com.paya.domain.models.repo.CircleChartDataRepoModel
import com.paya.domain.models.repo.LineChart
import com.paya.domain.models.repo.LinearChartRepoModel
import javax.inject.Inject

class BoxHistoryRemoteRepoMapper @Inject constructor(): Mapper<
		BoxHistoryRemoteModel,
		BoxHistoryRepoModel>{
	
	override fun map(param: BoxHistoryRemoteModel): BoxHistoryRepoModel {
		val cardChart = LinearChartRepoModel(
			data = param.cardChart.data.map {
				LineChart(it[0] as Double, it[1] as String)
			},
			startDate = param.cardChart.startDate,
			endDate = param.cardChart.endDate
		)
		val mainChart = LinearChartRepoModel(
			data = param.mainChart.data.map {
				LineChart(it[0] as Double, it[1] as String)
			},
			startDate = param.mainChart.startDate,
			endDate = param.mainChart.endDate
		)
		val circleChart = param.circleChart.map {
			CircleChartDataRepoModel(
				buyPrice = it.buyPrice,
				quantity = it.quantity,
				name = it.name,
				color = it.color
			)
		}
		
		return BoxHistoryRepoModel(
			cardChart = cardChart,
			mainChart = mainChart,
			circleChart = circleChart,
			buyValue = param.buyValue.let {
				LineChart(it[0] as Double, it[1] as String)
			},
			percent = param.percent,
			name = param.name
		)
	}
	
}