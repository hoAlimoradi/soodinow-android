package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName

data class RiskAssessmentResponseRemoteModel (
    @SerializedName("pages") val pages : List<RiskAssessmentPagesRemoteModel>,
    @SerializedName("count") val count : Int,
    @SerializedName("page_count") val pageCount : Int

)

data class RiskAssessmentRequestAnswerBodyModel (
    val answers : List<RiskAssessmentRequestAnswer>
)
data class RiskAssessmentRequestAnswer (
    val question : AnswersQuestion,
    val answers : List<AnswersQuestion>
)

data class AnswersQuestion (
    val id : Int,
    val type : String
)

data class RiskAssessmentSubmitResponseRemoteModel (
    @SerializedName("result_id") val resultId : Int,
    @SerializedName("risk_value") val riskValue : Float,
    @SerializedName("investment_host_id") val investmentHostId : Int,
    @SerializedName("error_messages") val errorMessages : List<String>,
    @SerializedName("warning_messages") val warningMessages : List<String>
)








