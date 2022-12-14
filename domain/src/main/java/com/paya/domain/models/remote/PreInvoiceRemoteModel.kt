package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class PreInvoiceRemoteModel(
	@SerializedName("uuid") val uuid: String?,
	@SerializedName("host_id") val hostId: List<String>?,
	val price: List<String>?
): NoObfuscate