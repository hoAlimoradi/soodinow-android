package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class LinearChartRemoteModel (
	val data: List<List<*>>?,
	@SerializedName("start_date")
	val startDate: String?,
	@SerializedName("end_date")
	val endDate: String?
): NoObfuscate
