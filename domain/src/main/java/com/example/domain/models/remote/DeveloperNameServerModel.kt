package com.example.domain.models.remote

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class DeveloperNameServerModel(
    @Json(name = "first")
    val first: String? = "",
    @Json(name = "last")
    val last: String? = ""
)