package com.paya.domain.models.repo

import com.paya.domain.models.remote.RiskAssessmentAnswers

data class RiskAssessmentQuestionRepoModel(
    val id : Int,
    val title : String,
    val type : String,
    val answers : List<RiskAssessmentAnswers>,
    var IsAnsweredByTheUser: Boolean = false
)