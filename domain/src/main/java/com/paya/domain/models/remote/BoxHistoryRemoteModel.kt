package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName

data class BoxHistoryRemoteModel(
	@SerializedName("up_chart")
	val cardChart: LinearChartRemoteModel,
	@SerializedName("down_chart")
	val mainChart: LinearChartRemoteModel,
	@SerializedName("circle_chart")
	val circleChart: List<CircleChartDataRemoteModel>,
	@SerializedName("buy_value")
	val buyValue: Long,
	val percent: Float
)