package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class ActivateRemoteModel(
	@SerializedName("username")
	val username: String?,
	@SerializedName("phone_number")
	val phoneNumber: String?,
	@SerializedName("code")
	val code: String?
): NoObfuscate