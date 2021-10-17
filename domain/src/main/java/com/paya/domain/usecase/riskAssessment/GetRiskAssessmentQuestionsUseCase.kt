package com.paya.domain.usecase.riskAssessment
 
import com.paya.domain.models.remote.RiskAssessmentResponseRepoModel
import com.paya.domain.repository.RiskAssessmentRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class GetRiskAssessmentQuestionsUseCase @Inject constructor(
    private val riskAssessmentRepository: RiskAssessmentRepository
): UseCase<Unit, RiskAssessmentResponseRepoModel> {
    override suspend fun action(param: Unit): Resource<RiskAssessmentResponseRepoModel> {
        return riskAssessmentRepository.getRiskAssessmentQuestions()
    }

}
