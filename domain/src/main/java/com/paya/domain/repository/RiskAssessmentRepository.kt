package com.paya.domain.repository

import com.paya.domain.models.remote.RiskAssessmentRequestAnswer
import com.paya.domain.models.remote.RiskAssessmentResponseRepoModel
import com.paya.domain.models.remote.RiskAssessmentSubmitResponseRepoModel
import com.paya.domain.tools.Resource

interface RiskAssessmentRepository {
    suspend fun getRiskAssessmentQuestions(): Resource<RiskAssessmentResponseRepoModel>

    suspend fun submitRiskAssessmentQuestions(answers: List<RiskAssessmentRequestAnswer>): Resource<RiskAssessmentSubmitResponseRepoModel>
}