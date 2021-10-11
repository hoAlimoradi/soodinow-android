package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class InvestingInfoRemoteModel(
    @SerializedName("investment_boxes_data") val investmentBoxesData: List<InvestingInfoDataRemoteModel>?,
    @SerializedName("wallet_data") val walletData: InvestingInfoDataRemoteModel?,
    @SerializedName("total_free_money") val totalFreeMoney: Long?,
    @SerializedName("total_block_money") val totalBlockMoney: Long?,
    @SerializedName("total_money") val totalMoney: Long?,
    @SerializedName("total_profit") val totalProfit: Long?,
) : NoObfuscate

data class InvestingInfoDataRemoteModel(
    val id: Int?,
    val balances: BalancesInfoDataRemoteModel?,
    @SerializedName("object_type") val objectType: String?,
    @SerializedName("host") val host: HostInfoDataRemoteModel?,
    @SerializedName("created_at") val createdAt: String?,
) : NoObfuscate

data class BalancesInfoDataRemoteModel(
    @SerializedName("free_balance") val freeBalance: Long?,
    @SerializedName("block_balance") val blockBalance: Long?,
    @SerializedName("free_balance") val totalBalance: Long?,
)
data class HostInfoDataRemoteModel(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?
)