package com.paya.domain.models.repo

data class RiskAssessmentResponseRepoModel (
    val pages : List<RiskAssessmentPagesRepoModel>,
    val count : Int,
    val pageCount : Int
)