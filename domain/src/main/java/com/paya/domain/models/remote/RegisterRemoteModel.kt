package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName

data class RegisterRemoteModel(
	@SerializedName("username")
	val username: String
)