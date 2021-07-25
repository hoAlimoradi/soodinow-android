package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class ProfileExtraRemoteModel(
    @SerializedName("first_name") val firstName: List<String>?,
    @SerializedName("last_name") val lastName: List<String>?,
    @SerializedName("phone_number") val phone: List<String>?,
    val email: List<String>?,
    @SerializedName("personal_code") val personalCode: List<String>?,
    @SerializedName("birth_day") val birthDay: List<String>?,
    val bban: List<String>?,
    val mobile: List<String>?,
    val gender: List<String>?,
    val state: List<String>?,
    val city: List<String>?,
    val address: List<String>?,
    val company: List<String>?,

    ) : NoObfuscate, ExtraRemoteInterface