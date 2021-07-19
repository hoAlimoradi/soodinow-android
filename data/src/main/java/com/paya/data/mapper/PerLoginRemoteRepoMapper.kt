package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.PerLoginRemoteModel
import com.paya.domain.models.repo.PerLoginRepoModel
import javax.inject.Inject

class PerLoginRemoteRepoMapper @Inject constructor() : Mapper<
        PerLoginRemoteModel,
        PerLoginRepoModel
        > {

    override fun map(param: PerLoginRemoteModel): PerLoginRepoModel {
        return PerLoginRepoModel(
            param.username ?: ""
        )
    }

}