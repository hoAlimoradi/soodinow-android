package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class CommonMarktDataPriceRemoteModel(
	@SerializedName("title")
	val name: String?,
	@SerializedName("slug")
	val slug: String?,
	@SerializedName("p")
	val priceUsd: Float?,
	@SerializedName("percent")
	val percentChange: Float?,
): NoObfuscate

