package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName

data class RiskAssessmentAnswers(
    @SerializedName("id") val id : Int,
    @SerializedName("title") val title : String,
    @SerializedName("score") val score : Int,
    @SerializedName("other field") val otherField : String
)
