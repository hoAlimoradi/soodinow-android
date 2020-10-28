package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName

data class AccountCardRemoteModel(
	@SerializedName("number")
	val number: String
)