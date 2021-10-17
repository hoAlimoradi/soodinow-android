package com.paya.data.network.remote_api

import com.paya.data.network.apiresponse.ApiResponse
import com.paya.domain.models.remote.*
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RiskAssessmentService {
    @GET("q/1/questions")
    suspend fun getRiskAssessmentQuestions(@Header("Authorization") auth: String): ApiResponse<BaseModel<RiskAssessmentResponseRemoteModel>>

    @POST("q/1/submit")
    suspend fun submitRiskAssessmentQuestions(@Header("Authorization") auth: String,
                                              @Field("answers") answers: List<RiskAssessmentRequestAnswer>
    ): ApiResponse<BaseModel<RiskAssessmentSubmitResponseRemoteModel>>

}
