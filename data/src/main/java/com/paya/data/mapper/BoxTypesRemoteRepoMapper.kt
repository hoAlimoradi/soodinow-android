package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.BoxHistoryRemoteModel
import com.paya.domain.models.remote.BoxTypeRemoteModel
import com.paya.domain.models.repo.*
import javax.inject.Inject

class BoxTypesRemoteRepoMapper @Inject constructor() : Mapper<
        BoxTypeRemoteModel,
        BoxTypeRepoModel> {

    override fun map(param: BoxTypeRemoteModel): BoxTypeRepoModel {
        var list = mutableListOf<BoxTypeParam>()
        param.types.forEach() {
            list.add(BoxTypeParam(it.type, it.name))
        }
        return BoxTypeRepoModel(list)
    }

}