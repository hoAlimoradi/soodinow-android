package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName

data class PercentRemoteModel(
	val minimum: PercentEntity,
	val year: PercentEntity,
	val perfect: PercentEntity
)

data class PercentEntity(
	val percent: Float,
	val price: Float
)
