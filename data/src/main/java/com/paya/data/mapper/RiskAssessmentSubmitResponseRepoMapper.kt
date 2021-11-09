package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.RiskAssessmentSubmitResponseRemoteModel
import com.paya.domain.models.repo.RiskAssessmentSubmitResponseRepoModel
import javax.inject.Inject

class RiskAssessmentSubmitResponseRepoMapper @Inject constructor(): Mapper<
        RiskAssessmentSubmitResponseRemoteModel,
        RiskAssessmentSubmitResponseRepoModel
        > {
    override fun map(param: RiskAssessmentSubmitResponseRemoteModel): RiskAssessmentSubmitResponseRepoModel {
        return RiskAssessmentSubmitResponseRepoModel(
            resultId = param.resultId ,
            riskValue= param.riskValue,
            investmentHostId = param.investmentHostId ,
            errorMessages = param.errorMessages ,
            warningMessages = param.warningMessages
            )
    }
}
