package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName

data class AccessTokenRemoteModel(
	@SerializedName("access")
	val accessToken: String
)