package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class SetResetPasswordRemoteModel(
	@SerializedName("message")
	val message: String?
): NoObfuscate