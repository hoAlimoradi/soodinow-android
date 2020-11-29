package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName

data class ExitAccountRemoteModel(
	val existing: Boolean,
	@SerializedName("id")
	val activeBoxId: Long
)