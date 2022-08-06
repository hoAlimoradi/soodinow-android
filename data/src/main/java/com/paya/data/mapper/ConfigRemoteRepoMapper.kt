package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.ConfigRemoteModel
import com.paya.domain.models.repo.ConfigRepoModel
import javax.inject.Inject

class ConfigRemoteRepoMapper @Inject constructor() : Mapper<
        ConfigRemoteModel,
        ConfigRepoModel
        > {

    override fun map(param: ConfigRemoteModel): ConfigRepoModel {
        return ConfigRepoModel(
            param.appLink
        )
    }

}