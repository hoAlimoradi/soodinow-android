package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.CheckVersionRemoteModel
import com.paya.domain.models.repo.CheckVersionRepoModel
import javax.inject.Inject

class CheckVersionRemoteRepoMapper @Inject constructor() : Mapper<
        CheckVersionRemoteModel,
        CheckVersionRepoModel
        > {

    override fun map(param: CheckVersionRemoteModel): CheckVersionRepoModel {
        return CheckVersionRepoModel(
            !param.link.isNullOrEmpty(),
            param.isForce,
            param.link ?: ""
        )
    }

}