package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.ActivateResetPhoneRemoteModel
import com.paya.domain.models.remote.ResetPhoneVerifyRemoteModel
import com.paya.domain.models.repo.ActivateResetPhoneRepoModel
import com.paya.domain.models.repo.ResetPhoneVerifyRepoModel
import javax.inject.Inject

class ActivateResetPhoneRemoteRepoMapper @Inject constructor() : Mapper<
        String,
        ActivateResetPhoneRepoModel
        > {

    override fun map(param: String): ActivateResetPhoneRepoModel {
        return ActivateResetPhoneRepoModel(
             "",
             ""
        )
    }

}