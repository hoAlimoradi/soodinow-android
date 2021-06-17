package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.BoxTypeRemoteModel
import com.paya.domain.models.repo.BoxTypeParam
import com.paya.domain.models.repo.BoxTypeRepoModel
import javax.inject.Inject

class BoxTypesRemoteRepoMapper @Inject constructor() : Mapper<
        BoxTypeRemoteModel,
        BoxTypeRepoModel> {

    override fun map(param: BoxTypeRemoteModel): BoxTypeRepoModel {
        return BoxTypeRepoModel(param.types?.map {
            BoxTypeParam(it.type, it.name)
        } ?: emptyList())
    }

}