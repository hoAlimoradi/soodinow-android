package com.paya.data.network.remote_api

import com.paya.data.network.apiresponse.ApiResponse
import com.paya.domain.models.remote.BaseModel
import com.paya.domain.models.remote.QuestionsRemoteModel
import retrofit2.http.GET

interface QuestionService {
	
	@GET("/q/questions/")
	suspend fun getAllQuestions(): ApiResponse<BaseModel<QuestionsRemoteModel>>
	
}