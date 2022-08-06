package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class ChangePasswordRemoteBodyModel(
	@SerializedName("old_password") val oldPassword: String,
	val password: String,
): NoObfuscate