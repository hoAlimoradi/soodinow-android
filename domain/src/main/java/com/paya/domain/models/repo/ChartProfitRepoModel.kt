package com.paya.domain.models.repo

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class ChartProfitRepoModel(
	val day: Int,
	val points: List<ChartProfitPointRepoModel>,
	val name: String
): NoObfuscate

data class ChartProfitPointRepoModel(
	val date: String,
	val price: Double
)