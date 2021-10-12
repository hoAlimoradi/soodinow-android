package com.paya.domain.usecase.riskAssessment

import com.paya.domain.models.remote.RiskAssessmentRequestAnswerRepoBodyModel
import com.paya.domain.models.remote.RiskAssessmentSubmitResponseRepoModel
import com.paya.domain.repository.RiskAssessmentRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class SubmitRiskAssessmentAnswersQuestionsUseCase @Inject constructor(
    private val riskAssessmentRepository: RiskAssessmentRepository
): UseCase<RiskAssessmentRequestAnswerRepoBodyModel, RiskAssessmentSubmitResponseRepoModel> {
    override suspend fun action(param: RiskAssessmentRequestAnswerRepoBodyModel): Resource<RiskAssessmentSubmitResponseRepoModel> {
        return riskAssessmentRepository.submitRiskAssessmentQuestions(param.answers)
    }

}
