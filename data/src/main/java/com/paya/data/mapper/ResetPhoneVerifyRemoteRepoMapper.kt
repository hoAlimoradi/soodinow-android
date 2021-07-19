package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.ResetPhoneVerifyRemoteModel
import com.paya.domain.models.repo.ResetPhoneVerifyRepoModel
import javax.inject.Inject

class ResetPhoneVerifyRemoteRepoMapper @Inject constructor() : Mapper<
        ResetPhoneVerifyRemoteModel,
        ResetPhoneVerifyRepoModel
        > {

    override fun map(param: ResetPhoneVerifyRemoteModel): ResetPhoneVerifyRepoModel {
        return ResetPhoneVerifyRepoModel(
            param.phoneNumber ?: "",
            param.code ?: ""
        )
    }

}