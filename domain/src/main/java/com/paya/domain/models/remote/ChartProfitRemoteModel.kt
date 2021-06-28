package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class ChartProfitRemoteModel(
	val day: Int?,
	val points: List<ChartProfitPointRemoteModel>?,
	val name: String?
): NoObfuscate

data class ChartProfitPointRemoteModel(
	val date: String?,
	val price: Double?
)