package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName

data class LinearChartRemoteModel (
	val data: List<Long>,
	@SerializedName("start_date")
	val startDate: String,
	@SerializedName("end_date")
	val endDate: String
)
