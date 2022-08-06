package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.InvestmentLogsRemoteModel
import com.paya.domain.models.repo.InvestmentHeaderDate
import com.paya.domain.models.repo.InvestmentLogsModel
import com.paya.domain.models.repo.InvestmentLogsRepoModel
import javax.inject.Inject
import kotlin.random.Random

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

   /* fun mockData(): InvestmentLogsRemoteModel {
        val list = mutableListOf<com.paya.domain.models.remote.InvestmentLogsModel>()

        for (index in 1..25) {
            list.add( com.paya.domain.models.remote.InvestmentLogsModel(
                Random(1000).nextLong(),
                Random(1000000).nextLong(),
                Random(1000000).nextLong(),
                "Open",
                "Fixed",
                "no-risk",
                "",
                "Pending",
                "12321432",
                false,
                "",
                "2021-07-17T11:59:28.655028+04:30",
                "2021-07-17T11:59:28.655028+04:30",
                ""

                )
            )
        }
        return InvestmentLogsRemoteModel(
            100,
            "10",
            "10",
            list
        )
    }*/
}