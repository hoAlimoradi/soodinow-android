package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.SetResetPasswordRemoteModel
import com.paya.domain.models.repo.SetResetPasswordRepoModel
import javax.inject.Inject

class SetResetPasswordRemoteRepoMapper @Inject constructor() : Mapper<
        SetResetPasswordRemoteModel,
        SetResetPasswordRepoModel
        > {
    override fun map(param: SetResetPasswordRemoteModel): SetResetPasswordRepoModel {
        return SetResetPasswordRepoModel(
            message = param.message ?: ""
        )
    }
}