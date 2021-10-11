package com.paya.domain.models.repo

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class WalletHostListRepoModel(
    val id: Int,
    val name: String,
    val descriptionTitle: String,
    val descriptionBody: String,
    val accessLevel: String,
    val fromRisk: Double,
    val toRisk: Double,
    val properties: List<String>,
    val basket: BasketHostsRepoModel,
    val efficiency: EfficiencyHostListRepoModel,
) : NoObfuscate

data class BasketHostsRepoModel(
    val fixIncome: Double,
    val gold: Double,
    val sahami: Double
)

data class EfficiencyHostListRepoModel(
    val week: EfficiencyModel,
    val month: EfficiencyModel,
    val threeMonth: EfficiencyModel,

) : NoObfuscate

data class EfficiencyModel(
    val percent: Double,
    val negative: Boolean,
) : NoObfuscate