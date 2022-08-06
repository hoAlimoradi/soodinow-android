package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class WalletPortfolioRemoteModel(
    @SerializedName("invest") val invest: List<WalletPortfolioInvestRemoteModel>?,
    @SerializedName("cash") val cash: WalletPortfolioCashRemoteModel?,
    @SerializedName("total_value") val totalValue: Double?
) : NoObfuscate

data class WalletPortfolioInvestRemoteModel(
    @SerializedName("id") val id: Long?,
    @SerializedName("title") val title: String?,
    @SerializedName("value") val value: Double?,
    @SerializedName("percent") val percent: List<WalletPortfolioPercentRemoteModel>?,
    @SerializedName("block_value") val blockValue: Long?,
) : NoObfuscate

data class WalletPortfolioPercentRemoteModel(
    @SerializedName("isin") val isin: String?,
    @SerializedName("namad") val namad: String?,
    @SerializedName("price") val price: Long?,
    @SerializedName("percent") val percent: Double?,
    @SerializedName("quantity") val quantity: Long?,
) : NoObfuscate


data class WalletPortfolioCashRemoteModel(
    @SerializedName("balance") val balance: Long?,
    @SerializedName("block") val block: Long?,
) : NoObfuscate
