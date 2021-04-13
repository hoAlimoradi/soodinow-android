package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName

data class CircleChartDataRemoteModel (
	@SerializedName("buy_price")
	val buyPrice: Float,
	@SerializedName("quantity")
	val quantity: Long,
	@SerializedName("namad")
	val name: String
)
