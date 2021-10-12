package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class WalletHostDetailRemoteModel(
    val id: Int?,
    val name: String?,
    @SerializedName("description_title") val descriptionTitle: String?,
    @SerializedName("description_body") val descriptionBody: String?,
    @SerializedName("access_level") val accessLevel: String?,
    @SerializedName("from_risk") val fromRisk: Double?,
    @SerializedName("to_risk") val toRisk: Double?,
    @SerializedName("basket_with_detail") val basketDetail: List<BasketHostsDetailRemoteModel>?,
    val basket: BasketHostsRemoteModel?,
    val properties: List<String>?,
    val efficiency: EfficiencyHostListRemoteModel?,
) : NoObfuscate

data class BasketHostsDetailRemoteModel(
    val namad: String?,
    val percent: Float?
)
