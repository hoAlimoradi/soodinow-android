package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName

data class ProfileRemoteModel(
	val id:Int,
	val name: String,
	@SerializedName("personal_code")val personalCode: String,
	@SerializedName("birth_day")val birthDay: String,
	val bban: String,
	val complete: Boolean,
	val mobile: String?,
	val gender: String,
	val state: String,
	val city: String,
	val address: String
	
)