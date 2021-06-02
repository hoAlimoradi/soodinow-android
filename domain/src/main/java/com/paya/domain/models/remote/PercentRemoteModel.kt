package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class PercentRemoteModel(
	@SerializedName("one_week")
	val oneWeek: PercentEntity?,
	@SerializedName("one_month")
	val oneMonth: PercentEntity?,
	@SerializedName("three_month")
	val threeMonth: PercentEntity?
): NoObfuscate

data class PercentEntity(
	val percent: Float?,
	val price: Float?
): NoObfuscate
