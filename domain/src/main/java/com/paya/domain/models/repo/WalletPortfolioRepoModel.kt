package com.paya.domain.models.repo

import com.paya.domain.tools.NoObfuscate

data class WalletPortfolioRepoModel(
    val invest: List<WalletPortfolioInvestRepoModel>,
    val cash: WalletPortfolioCashRepoModel,
    val totalValue: Double
) : NoObfuscate

data class WalletPortfolioInvestRepoModel(
    val id: Long,
    val title: String,
    val value: Double,
    val percent: List<WalletPortfolioPercentRepoModel>,
    val blockValue: Long,
) : NoObfuscate

data class WalletPortfolioPercentRepoModel(
    val isin: String,
    val namad: String,
    val price: Long,
    val percent: Double,
    val quantity: Long,
) : NoObfuscate


data class WalletPortfolioCashRepoModel(
    val balance: Long,
    val block: Long,
) : NoObfuscate 
