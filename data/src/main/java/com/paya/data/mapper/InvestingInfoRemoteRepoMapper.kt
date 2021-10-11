package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.InvestingInfoDataRemoteModel
import com.paya.domain.models.remote.InvestingInfoRemoteModel
import com.paya.domain.models.repo.InvestingInfoDataRepoModel
import com.paya.domain.models.repo.InvestingInfoRepoModel
import javax.inject.Inject

class InvestingInfoRemoteRepoMapper @Inject constructor() : Mapper<
        InvestingInfoRemoteModel,
        InvestingInfoRepoModel
        > {

    override fun map(param: InvestingInfoRemoteModel): InvestingInfoRepoModel {
        return InvestingInfoRepoModel(
            param.investmentBoxesData?.let {
                it.map { data ->
                    convertData(data)
                }
            } ?: emptyList(),
            param.walletData?.let { convertData(it) } ?: emptyData(),
            param.totalFreeMoney ?: 0L,
            param.totalBlockMoney ?: 0L,
            param.totalMoney ?: 0L,
            param.totalProfit ?: 0L,
        )
    }

    private fun convertData(data: InvestingInfoDataRemoteModel): InvestingInfoDataRepoModel {
        return InvestingInfoDataRepoModel(
            data.id ?: 0,
            data.balances?.freeBalance ?: 0,
            data.balances?.blockBalance ?: 0,
            data.balances?.totalBalance ?: 0,
            data.objectType ?: "",
            data.host?.id ?: 0,
            data.host?.name ?: "",
            data.createdAt ?: "",
        )
    }

    private fun emptyData(): InvestingInfoDataRepoModel {
        return InvestingInfoDataRepoModel(
            0,
            0,
            0,
            0,
            "",
            0,
            "",
            ""
        )
    }

}