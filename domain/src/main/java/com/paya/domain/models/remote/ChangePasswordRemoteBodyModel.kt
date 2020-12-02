package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName

data class ChangePasswordRemoteBodyModel(
	@SerializedName("old_password") val oldPassword: String,
	val password: String,
)