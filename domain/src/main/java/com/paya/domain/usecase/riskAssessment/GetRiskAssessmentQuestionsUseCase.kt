package com.paya.domain.usecase.riskAssessment

import com.paya.domain.models.remote.RiskAssessmentPages
import com.paya.domain.models.remote.RiskAssessmentResponseRemoteModel
import com.paya.domain.models.repo.QuestionsRepoModel
import com.paya.domain.repository.RiskAssessmentRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class GetRiskAssessmentQuestionsUseCase @Inject constructor(
    private val riskAssessmentRepository: RiskAssessmentRepository
): UseCase<Unit, RiskAssessmentResponseRemoteModel> {
    override suspend fun action(param: Unit): Resource<RiskAssessmentResponseRemoteModel> {
        return riskAssessmentRepository.getRiskAssessmentQuestions()
    }

}
