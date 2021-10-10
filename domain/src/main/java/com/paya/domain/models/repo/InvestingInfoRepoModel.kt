package com.paya.domain.models.repo

import com.paya.domain.tools.NoObfuscate

data class InvestingInfoRepoModel(
    val investmentBoxesData: List<InvestingInfoDataRepoModel>,
    val walletData: InvestingInfoDataRepoModel,
    val totalFreeMoney: Long,
    val totalBlockMoney: Long,
    val totalMoney: Long,
    val totalProfit: Long,
) : NoObfuscate

data class InvestingInfoDataRepoModel(
    val id: Int?,
    val freeBalance: Long,
    val blockBalance: Long,
    val totalBalance: Long,
    val objectType: String,
    val hostId: Int,
    val hostName: String,
    val createdAt: String,
) : NoObfuscate
