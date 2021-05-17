package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class BaseModel<T>(
	@SerializedName("data")
	val data: T,
	@SerializedName("error")
	val error: ErrorModel?
): NoObfuscate

data class ErrorModel(
	@SerializedName("message")
	val message: String,
	@SerializedName("code")
	val code: Int
): NoObfuscate
