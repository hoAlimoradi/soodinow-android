package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class PortalBankRemoteModel(
	@SerializedName("name")
	val name: String?,
	@SerializedName("persian_name")
	val persianName: String?,
	@SerializedName("img_url")
	val imgUrl: String?
): NoObfuscate

