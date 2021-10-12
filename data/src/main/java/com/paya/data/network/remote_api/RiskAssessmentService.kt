package com.paya.data.network.remote_api

import com.paya.data.network.apiresponse.ApiResponse
import com.paya.domain.models.remote.BaseModel
import com.paya.domain.models.remote.RiskAssessmentResponseRemoteModel
import retrofit2.http.GET
import retrofit2.http.POST

interface RiskAssessmentService {
    @GET("q/1/questions")
    suspend fun getRiskAssessmentQuestions(): ApiResponse<BaseModel<RiskAssessmentResponseRemoteModel>>

    @POST("q/1/submit")
    suspend fun submitRiskAssessmentQuestions()

}