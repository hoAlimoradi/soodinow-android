package com.paya.domain.models.repo

import com.paya.domain.models.remote.BasketHostsDetailRemoteModel
import com.paya.domain.tools.NoObfuscate

data class WalletHostDetailRepoModel(
    val id: Int?,
    val name: String?,
    val descriptionTitle: String,
    val descriptionBody: String,
    val accessLevel: String,
    val fromRisk: Double,
    val toRisk: Double,
    val properties: List<String>,
    val basket: BasketHostsRepoModel,
    val efficiency: EfficiencyHostListRepoModel,
    val basketDetail: List<BasketHostsDetailRepoModel>,
) : NoObfuscate

data class BasketHostsDetailRepoModel(
    val namad: String,
    val percent: Float,
    val color: String
)
