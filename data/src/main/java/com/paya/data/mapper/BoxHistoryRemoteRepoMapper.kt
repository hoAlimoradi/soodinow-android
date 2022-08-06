package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.BoxHistoryRemoteModel
import com.paya.domain.models.repo.*
import javax.inject.Inject

class BoxHistoryRemoteRepoMapper @Inject constructor(): Mapper<
		BoxHistoryRemoteModel,
		BoxHistoryRepoModel>{
	
	override fun map(param: BoxHistoryRemoteModel): BoxHistoryRepoModel {
		// chart card
		var cardChart = LinearChartRepoModel(emptyList(), "", "")
		param.cardChart?.let { cardChartRemote ->
			cardChart = LinearChartRepoModel(
				data = cardChartRemote.data?.let { data ->
					data.map {
						var price: Double = -1.0
						var date = ""
						if (it.isNotEmpty() && it[0] != null && it[0] is Double) {
							price = it[0] as Double
						}
						if (it.isNotEmpty() && it.size > 1 && it[1] != null && it[1] is String) {
							date = it[1] as String
						}
						LineChart(price, date)
					}
				} ?: emptyList(),
				startDate = cardChartRemote.startDate ?: "",
				endDate = cardChartRemote.endDate ?: ""
			)
		}
		var mainChart = LinearChartRepoModel(emptyList(), "", "")
		param.mainChart?.let { mainChartRemote ->
			mainChart = LinearChartRepoModel(
				data = mainChartRemote.data?.let { data ->
					data.map {
						var price: Double = -1.0
						var date = ""
						if (it.isNotEmpty() && it[0] != null && it[0] is Double) {
							price = it[0] as Double
						}
						if (it.isNotEmpty() && it.size > 1 && it[1] != null && it[1] is String) {
							date = it[1] as String
						}
						LineChart(price, date)
					}
				} ?: emptyList(),
				startDate = mainChartRemote.startDate ?: "",
				endDate = mainChartRemote.endDate ?: ""
			)
		}
		var circleChart = emptyList<CircleChartDataRepoModel>()
		param.circleChart?.let { circleChartRemote ->
			circleChart = circleChartRemote.map {
				CircleChartDataRepoModel(
					buyPrice = it.buyPrice ?: 0f,
					quantity = it.quantity ?: 0,
					name = it.name ?: "",
					color = it.color ?: "#3bb400"
				)
			}
		}

		return BoxHistoryRepoModel(
			cardChart = cardChart,
			mainChart = mainChart,
			circleChart = circleChart,
			percent = param.percent ?: 0f,
			name = param.name ?: ""
		)
	}
	
}