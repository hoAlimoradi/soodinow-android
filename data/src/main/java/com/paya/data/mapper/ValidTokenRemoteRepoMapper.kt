package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.repo.ValidTokenRepoModel
import javax.inject.Inject

class ValidTokenRemoteRepoMapper @Inject constructor() : Mapper<
        String,
        ValidTokenRepoModel
        > {

    override fun map(param: String): ValidTokenRepoModel {
        return ValidTokenRepoModel(
            param ?: ""
        )
    }

}