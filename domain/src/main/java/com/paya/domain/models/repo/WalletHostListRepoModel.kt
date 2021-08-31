package com.paya.domain.models.repo

import com.paya.domain.tools.NoObfuscate

data class WalletHostListRepoModel(
    val id: Int,
    val title: String,
    val description: String,
    val efficiency: EfficiencyHostListRepoModel,
) : NoObfuscate

data class EfficiencyHostListRepoModel(
    val week: EfficiencyModel,
    val month: EfficiencyModel,
    val threeMonth: EfficiencyModel,
) : NoObfuscate

data class EfficiencyModel(
    val percent: Double,
    val negative: Boolean,
) : NoObfuscate