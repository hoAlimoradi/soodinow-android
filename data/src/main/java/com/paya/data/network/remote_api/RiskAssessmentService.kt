package com.paya.data.network.remote_api

import com.paya.data.network.apiresponse.ApiResponse
import com.paya.domain.models.remote.*
import retrofit2.http.*

interface RiskAssessmentService {
    @GET("q/1/questions/")
    suspend fun getRiskAssessmentQuestions(@Header("Authorization") auth: String): ApiResponse<BaseModel<RiskAssessmentResponseRemoteModel>>

    //@FormUrlEncoded
    @POST("q/1/submit/")
    suspend fun submitRiskAssessmentQuestions(@Header("Authorization") auth: String,
                                              @Body answers: RiskAssessmentRequestAnswerBodyModel
    ): ApiResponse<BaseModel<RiskAssessmentSubmitResponseRemoteModel>>

}
