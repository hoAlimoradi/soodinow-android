package com.paya.domain.models.repo

data class AboutUsModel(
    val title: String,
    val description: String,
    val hasBox: Boolean = true
)