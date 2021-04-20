package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.repo.UserFarabiRepoModel
import javax.inject.Inject

class UserFarabiRemoteRepoMapper @Inject constructor() : Mapper<
        List<*>,
        UserFarabiRepoModel
        > {

    override fun map(param: List<*>): UserFarabiRepoModel {
        return UserFarabiRepoModel(
            param[0] as Double,
			((param[5] as List<*>)[0] as List<*>)[0] as Double,
			param[1] as Double,
			param[3] as String
        )
    }

}