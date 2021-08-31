package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class WalletHostListRemoteModel(
    val id: Int?,
    val title: String?,
    val description: String?,
    val efficiency: EfficiencyHostListRemoteModel?,
) : NoObfuscate

data class EfficiencyHostListRemoteModel(
    val month: EfficiencyModel?,
    val week: EfficiencyModel?,
    @SerializedName("3_month") val threeMonth: EfficiencyModel?,
) : NoObfuscate

data class EfficiencyModel(
    val percent: Double?,
    val negative: Boolean?,
) : NoObfuscate