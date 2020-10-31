package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName

data class MarketRemoteModel(
	@SerializedName("number")
	val number: String
)