package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class PercentRemoteModel(
	val minimum: PercentEntity,
	val year: PercentEntity,
	val perfect: PercentEntity
): NoObfuscate

data class PercentEntity(
	val percent: Float,
	val price: Float
): NoObfuscate
