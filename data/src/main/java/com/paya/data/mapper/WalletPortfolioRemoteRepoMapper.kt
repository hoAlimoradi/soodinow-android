package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.WalletPortfolioRemoteModel
import com.paya.domain.models.repo.WalletPortfolioCashRepoModel
import com.paya.domain.models.repo.WalletPortfolioInvestRepoModel
import com.paya.domain.models.repo.WalletPortfolioPercentRepoModel
import com.paya.domain.models.repo.WalletPortfolioRepoModel
import javax.inject.Inject

class WalletPortfolioRemoteRepoMapper @Inject constructor() : Mapper<
        WalletPortfolioRemoteModel,
        WalletPortfolioRepoModel
        > {

    override fun map(param: WalletPortfolioRemoteModel): WalletPortfolioRepoModel {
        var invest = param.invest?.map { invest ->
            WalletPortfolioInvestRepoModel(
                invest.id ?: 0,
                invest.title ?: "",
                invest.value ?: 0.0,
                invest.percent?.map { percent ->
                    WalletPortfolioPercentRepoModel(
                        percent.isin ?: "",
                        percent.namad ?: "",
                        percent.price ?: 0L,
                        percent.percent ?: 0.0,
                        percent.quantity ?: 0
                    )
                } ?: emptyList(),
                invest.blockValue ?: 0L
            )
        } ?: emptyList()
        return WalletPortfolioRepoModel(
            invest,
            param.cash?.let { cash ->
                WalletPortfolioCashRepoModel(
                    cash.balance ?: 0,
                    cash.block ?: 0
                )
            } ?: WalletPortfolioCashRepoModel(0, 0),
            param.totalValue ?: 0.0
        )
    }

}