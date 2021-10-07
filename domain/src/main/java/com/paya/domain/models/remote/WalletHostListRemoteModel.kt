package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class WalletHostListRemoteModel(
    val id: Int?,
    val name: String?,
    @SerializedName("description_title") val descriptionTitle: String?,
    @SerializedName("description_body") val descriptionBody: String?,
    @SerializedName("access_level") val accessLevel: String?,
    @SerializedName("from_risk") val fromRisk: Int?,
    @SerializedName("to_risk") val toRisk: Int?,
    val basket: BasketHostsRemoteModel?,
    val properties: List<String>?,
    val efficiency: EfficiencyHostListRemoteModel?,
) : NoObfuscate

data class BasketHostsRemoteModel(
    @SerializedName("fix_income") val fixIncome: Int?
)

data class EfficiencyHostListRemoteModel(
    val month: EfficiencyModel?,
    val week: EfficiencyModel?,
    @SerializedName("3_month") val threeMonth: EfficiencyModel?,
) : NoObfuscate

data class EfficiencyModel(
    val percent: Double?,
    val negative: Boolean?,
) : NoObfuscate