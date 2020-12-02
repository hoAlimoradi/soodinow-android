package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName

data class CurrencyPriceRemoteModel(
	@SerializedName("nameFa")
	val name: String,
	val price: Long,
	val changeStatus: String,
	val changePercent: Float,
	val changePrice: Long
)
