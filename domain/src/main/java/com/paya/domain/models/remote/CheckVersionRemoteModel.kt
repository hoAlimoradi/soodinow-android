package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class CheckVersionRemoteModel(
    @SerializedName("is_force") val isForce: Boolean,
    val link: String?

): NoObfuscate