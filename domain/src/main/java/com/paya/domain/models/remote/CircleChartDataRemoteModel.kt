package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class CircleChartDataRemoteModel (
	@SerializedName("buy_price")
	val buyPrice: Float?,
	@SerializedName("quantity")
	val quantity: Long?,
	@SerializedName("namad")
	val name: String?,
	@SerializedName("color")
	val color: String?
): NoObfuscate
