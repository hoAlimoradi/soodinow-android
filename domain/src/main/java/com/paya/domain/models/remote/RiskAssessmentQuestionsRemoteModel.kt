package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName

data class RiskAssessmentQuestionRemoteModel(
    @SerializedName("id") val id : Int,
    @SerializedName("title") val title : String,
    @SerializedName("type") val type : String,
    @SerializedName("answers") val answers : List<RiskAssessmentAnswers>
)