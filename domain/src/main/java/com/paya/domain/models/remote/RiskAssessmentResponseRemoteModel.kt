package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName

data class RiskAssessmentResponseRemoteModel (
    @SerializedName("pages") val pages : List<RiskAssessmentPages>,
    @SerializedName("count") val count : Int,
    @SerializedName("page_count") val pageCount : Int

)
