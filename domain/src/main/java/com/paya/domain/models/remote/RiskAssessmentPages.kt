package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName

data class RiskAssessmentPages(
    @SerializedName("page_number") val pageNumber : Int,
    @SerializedName("question_count") val questionCount : Int,
    @SerializedName("questions") val questions : List<RiskAssessmentQuestionRemoteModel>)


