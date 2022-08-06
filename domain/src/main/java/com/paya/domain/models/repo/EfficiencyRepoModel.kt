package com.paya.domain.models.repo

import java.io.Serializable

data class EfficiencyRepoModel(
    var title: String,
    val percent: Float,
    val price: Double,
    val isProfitable: Boolean
) : Serializable