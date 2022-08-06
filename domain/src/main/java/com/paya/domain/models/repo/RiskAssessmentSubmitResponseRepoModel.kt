package com.paya.domain.models.repo

data class RiskAssessmentSubmitResponseRepoModel (
    val resultId : Int,
    val riskValue : Float,
    val investmentHostId : Int,
    val errorMessages : List<String>,
    val warningMessages : List<String>
)