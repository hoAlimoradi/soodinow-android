package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.InvestmentLogsRemoteModel
import com.paya.domain.models.repo.InvestmentLogsRepoModel
import javax.inject.Inject

class InvestmentLogsRemoteRepoMapper @Inject constructor() : Mapper<
        @kotlin.jvm.JvmSuppressWildcards List<InvestmentLogsRemoteModel>,
        @JvmSuppressWildcards List<InvestmentLogsRepoModel>
        > {

    override fun map(param: List<InvestmentLogsRemoteModel>): List<InvestmentLogsRepoModel> {
        return param.map {
            InvestmentLogsRepoModel(
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
    }

}