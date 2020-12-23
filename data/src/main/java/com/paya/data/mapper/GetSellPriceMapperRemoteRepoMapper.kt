package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.BoxHistoryRemoteModel
import com.paya.domain.models.remote.BoxTypeRemoteModel
import com.paya.domain.models.repo.BoxHistoryRepoModel
import com.paya.domain.models.repo.BoxTypeRepoModel
import com.paya.domain.models.repo.CircleChartDataRepoModel
import com.paya.domain.models.repo.LinearChartRepoModel
import javax.inject.Inject

class GetSellPriceMapperRemoteRepoMapper @Inject constructor() :
    Mapper< @JvmSuppressWildcards List<List<Long>>,  @JvmSuppressWildcards List<Long>> {
    override fun map(param: List<List<Long>>): List<Long> {
        val list = mutableListOf<Long>()
        param.map {
            it.forEachIndexed() { _, price ->
                list.add(price)
                if (it[0] == it[it.size-1])
                    return@forEachIndexed
            }
        }
        return list
    }


}