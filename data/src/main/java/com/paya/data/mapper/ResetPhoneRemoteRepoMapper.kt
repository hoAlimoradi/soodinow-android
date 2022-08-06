package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.PerLoginRemoteModel
import com.paya.domain.models.repo.PerLoginRepoModel
import com.paya.domain.models.repo.ResetPhoneRepoModel
import javax.inject.Inject

class ResetPhoneRemoteRepoMapper @Inject constructor() : Mapper<
        String,
        ResetPhoneRepoModel
        > {

    override fun map(param: String): ResetPhoneRepoModel {
        return ResetPhoneRepoModel(
            true
        )
    }

}