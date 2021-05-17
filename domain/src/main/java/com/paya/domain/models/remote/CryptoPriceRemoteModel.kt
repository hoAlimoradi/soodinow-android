package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class CryptoPriceRemoteModel(
	val name: String,
	@SerializedName("price_usd")
	val priceUsd: String,
	@SerializedName("percent_change_24h")
	val percentChange: String
): NoObfuscate