package com.paya.domain.models.repo

data class EfficiencyRepoModel(
    var title: String,
    val percent: Float,
    val price: Double,
    val isProfitable: Boolean
)