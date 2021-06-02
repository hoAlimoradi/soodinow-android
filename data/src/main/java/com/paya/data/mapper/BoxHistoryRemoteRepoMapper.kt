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
						LineChart(it[0] as Double, it[1] as String)
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
						LineChart(it[0] as Double, it[1] as String)
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


		val isProfitable: Boolean =
			if (mainChart.data.isEmpty()) {
				true
			} else mainChart.data[0].price <= mainChart.data[mainChart.data.size - 1].price


		val priceEfficiency: Double = when {
			mainChart.data.isEmpty() -> {
				0.0
			}
			isProfitable -> mainChart.data[mainChart.data.size - 1].price - mainChart.data[0].price
			else -> mainChart.data[0].price - mainChart.data[mainChart.data.size - 1].price
		}

		val percentEfficiency: Double = when {
			mainChart.data.isEmpty() -> {
				0.0
			}
			else -> priceEfficiency / mainChart.data[0].price
		}
		val efficiencyRepoModel = EfficiencyRepoModel(
			"",
			(percentEfficiency * 100).toFloat(),
			priceEfficiency,
			isProfitable
		)

		return BoxHistoryRepoModel(
			cardChart = cardChart,
			efficiency = efficiencyRepoModel,
			mainChart = mainChart,
			circleChart = circleChart,
			percent = param.percent ?: 0f,
			name = param.name ?: ""
		)
	}
	
}