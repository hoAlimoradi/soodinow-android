package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName

data class TotalBoxValueRemoteModel(
	@SerializedName("total_value")val totalValue: Long,

	)