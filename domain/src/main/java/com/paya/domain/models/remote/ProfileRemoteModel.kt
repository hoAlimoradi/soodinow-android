package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class ProfileRemoteModel(
	val id:Int,
	@SerializedName("first_name")val firstName: String?,
	@SerializedName("last_name")val lastName: String?,
	@SerializedName("phone_number")val phone: String?,
	val email: String?,
	@SerializedName("personal_code")val personalCode: String?,
	@SerializedName("birth_day")val birthDay: String?,
	val bban: String?,
	val complete: Boolean,
	val mobile: String?,
	val gender: String?,
	val state: String?,
	val city: String?,
	val address: String?,
	val company: String?,

): NoObfuscate