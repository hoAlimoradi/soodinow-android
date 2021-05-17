package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class BoxHistoryRemoteModel(
	@SerializedName("up_chart")
	val cardChart: LinearChartRemoteModel,
	@SerializedName("down_chart")
	val mainChart: LinearChartRemoteModel,
	@SerializedName("circle_chart")
	val circleChart: List<CircleChartDataRemoteModel>,
	@SerializedName("buy_value")
	val buyValue: List<*>,
	val percent: Float,
	val name: String
): NoObfuscate