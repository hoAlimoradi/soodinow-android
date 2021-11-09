package com.paya.domain.usecase.riskAssessment

import com.paya.domain.models.remote.RiskAssessmentRequestAnswerBodyModel
import com.paya.domain.models.repo.RiskAssessmentSubmitResponseRepoModel
import com.paya.domain.repository.RiskAssessmentRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class SubmitRiskAssessmentAnswersQuestionsUseCase @Inject constructor(
    private val riskAssessmentRepository: RiskAssessmentRepository
): UseCase<RiskAssessmentRequestAnswerBodyModel, RiskAssessmentSubmitResponseRepoModel> {
    override suspend fun action(param: RiskAssessmentRequestAnswerBodyModel): Resource<RiskAssessmentSubmitResponseRepoModel> {
        return riskAssessmentRepository.submitRiskAssessmentQuestions(param.answers)
    }

}
