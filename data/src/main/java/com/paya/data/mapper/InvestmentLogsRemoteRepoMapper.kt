package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.InvestmentLogsRemoteModel
import com.paya.domain.models.repo.InvestmentHeaderDate
import com.paya.domain.models.repo.InvestmentLogsModel
import com.paya.domain.models.repo.InvestmentLogsRepoModel
import javax.inject.Inject

class InvestmentLogsRemoteRepoMapper @Inject constructor() : Mapper<
        @kotlin.jvm.JvmSuppressWildcards InvestmentLogsRemoteModel,
        @JvmSuppressWildcards InvestmentLogsRepoModel
        > {

    override fun map(param: InvestmentLogsRemoteModel): InvestmentLogsRepoModel {
        val list = mutableListOf<Any>()
        var lastDate = "";
        param.results?.let { results ->
            results.forEach {
                var currentDate = it.createdAt.substring(0, it.createdAt.indexOf("T"))
                if (lastDate.isEmpty() || lastDate != currentDate) {
                    list.add(InvestmentHeaderDate(it.createdAt))
                    lastDate = currentDate;
                }
                list.add(
                    InvestmentLogsModel(
                        it.id ?: 0,
                        it.startPrice ?: 0,
                        it.finalPrice ?: 0,
                        it.type ?: "",
                        it.investmentType ?: "",
                        it.investmentSubType ?: "",
                        it.user ?: "",
                        it.state ?: "",
                        it.trackingNumber ?: "",
                        it.hasError,
                        it.errorDescription ?: "",
                        it.createdAt,
                        it.updatedAt,
                        it.description ?: ""
                    )
                )
            }
        }
        return InvestmentLogsRepoModel(
            param.count,
            param.next ?: "",
            param.previous ?: "",
            list
        )
    }

}