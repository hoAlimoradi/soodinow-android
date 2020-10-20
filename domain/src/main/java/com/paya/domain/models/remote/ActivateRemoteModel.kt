package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName

data class ActivateRemoteModel(
	@SerializedName("username")
	val username: String,
	@SerializedName("code")
	val code: String
)