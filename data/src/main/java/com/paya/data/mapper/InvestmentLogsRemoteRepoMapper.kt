package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.InvestmentLogsRemoteModel
import com.paya.domain.models.repo.InvestmentLogsModel
import com.paya.domain.models.repo.InvestmentLogsRepoModel
import javax.inject.Inject

class InvestmentLogsRemoteRepoMapper @Inject constructor() : Mapper<
        @kotlin.jvm.JvmSuppressWildcards InvestmentLogsRemoteModel,
        @JvmSuppressWildcards InvestmentLogsRepoModel
        > {

    override fun map(param: InvestmentLogsRemoteModel): InvestmentLogsRepoModel {
        return InvestmentLogsRepoModel(
            param.count,
            param.next ?: "",
            param.previous ?: "",
            param.results.map {
                InvestmentLogsModel(
                    it.id,
                    it.startPrice,
                    it.finalPrice,
                    it.type,
                    it.investmentType,
                    it.investmentSubType ?: "",
                    it.user,
                    it.state,
                    it.trackingNumber,
                    it.hasError,
                    it.errorDescription ?: "",
                    it.createdAt,
                    it.updatedAt
                )
            }
        )
    }

}