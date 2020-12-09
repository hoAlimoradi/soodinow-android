package com.paya.data.network.remote_api

import com.paya.data.network.apiresponse.ApiResponse
import com.paya.domain.models.remote.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface QuestionService {
	
	@GET("q/questions/")
	suspend fun getAllQuestions(): ApiResponse<BaseModel<List<QuestionsRemoteModel>>>
	
	@POST("q/test/")
	suspend fun submitUserTest(
		@Body userTest: UserTest
	): ApiResponse<BaseModel<UserTestRemoteModel>>
	
}