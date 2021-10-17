package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.RiskAssessmentResponseRemoteModel
import com.paya.domain.models.remote.RiskAssessmentResponseRepoModel
import javax.inject.Inject

class RiskAssessmentResponseRepoMapper @Inject constructor(): Mapper<
        RiskAssessmentResponseRemoteModel,
        RiskAssessmentResponseRepoModel
        > {
    override fun map(param: RiskAssessmentResponseRemoteModel): RiskAssessmentResponseRepoModel {
        return RiskAssessmentResponseRepoModel(
            pages = param.pages,
            count = param.count,
            pageCount = param.pageCount
        )
    }
}
