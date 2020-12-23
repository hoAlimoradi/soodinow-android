package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.BoxHistoryRemoteModel
import com.paya.domain.models.remote.BoxTypeRemoteModel
import com.paya.domain.models.repo.BoxHistoryRepoModel
import com.paya.domain.models.repo.BoxTypeRepoModel
import com.paya.domain.models.repo.CircleChartDataRepoModel
import com.paya.domain.models.repo.LinearChartRepoModel
import javax.inject.Inject

class BoxTypesRemoteRepoMapper @Inject constructor() : Mapper<
        BoxTypeRemoteModel,
        BoxTypeRepoModel> {

    override fun map(param: BoxTypeRemoteModel): BoxTypeRepoModel {
        return BoxTypeRepoModel(param.types)
    }

}