package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.ChartProfitRemoteModel
import com.paya.domain.models.repo.ChartProfitPointRepoModel
import com.paya.domain.models.repo.ChartProfitRepoModel
import javax.inject.Inject

class ChartProfitRemoteRepoMapper @Inject constructor() : Mapper<
        @JvmSuppressWildcards List<ChartProfitRemoteModel>,
        @JvmSuppressWildcards List<ChartProfitRepoModel>
        > {

    override fun map(param: List<ChartProfitRemoteModel>): List<ChartProfitRepoModel> {
        val list = mutableListOf<ChartProfitRepoModel>()
        if (param.isNotEmpty()) {
            for (it in param.reversed()) {
                list.add(ChartProfitRepoModel(
                    it.day ?: 0,
                    it.points?.map { point ->
                        ChartProfitPointRepoModel(
                            point.date ?: "",
                            point.price ?: 0.0
                        )
                    } ?: emptyList(),
                    it.name ?: ""
                ))
            }
        }
        return list
    }

}