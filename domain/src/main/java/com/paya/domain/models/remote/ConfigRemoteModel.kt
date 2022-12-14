package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class ConfigRemoteModel(
    @SerializedName("app_link")
    val appLink: String

): NoObfuscate
