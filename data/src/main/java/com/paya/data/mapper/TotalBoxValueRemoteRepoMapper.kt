package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.TotalBoxValueRemoteModel
import com.paya.domain.models.repo.TotalBoxValueRepoModel
import javax.inject.Inject

class TotalBoxValueRemoteRepoMapper @Inject constructor() : Mapper<
        TotalBoxValueRemoteModel,
        TotalBoxValueRepoModel> {

    override fun map(param: TotalBoxValueRemoteModel): TotalBoxValueRepoModel {
        return TotalBoxValueRepoModel(param.totalValue ?: 0)
    }

}