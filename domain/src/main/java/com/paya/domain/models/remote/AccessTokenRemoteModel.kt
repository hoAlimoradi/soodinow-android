package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class AccessTokenRemoteModel(
	@SerializedName("access")
	val accessToken: String?,
	@SerializedName("refresh_token")
	val refreshToken: String?
): NoObfuscate