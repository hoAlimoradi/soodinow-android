package com.paya.domain.models.repo


data class RiskAssessmentPagesRepoModel(
    val pageNumber : Int,
    val questionCount : Int,
    val questions : List<RiskAssessmentQuestionRepoModel>)


