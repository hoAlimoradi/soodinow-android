package com.paya.domain.repository

import com.paya.domain.models.remote.RiskAssessmentPages
import com.paya.domain.models.remote.RiskAssessmentResponseRemoteModel
import com.paya.domain.tools.Resource

interface RiskAssessmentRepository {
    suspend fun getRiskAssessmentQuestions(): Resource<RiskAssessmentResponseRemoteModel>
}