package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName

data class SetPasswordRemoteModel(
	@SerializedName("message")
	val message: String
)