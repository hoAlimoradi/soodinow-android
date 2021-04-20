package com.paya.data.network.remote_api

import com.paya.data.network.apiresponse.ApiResponse
import com.paya.domain.models.remote.BaseModel
import com.paya.domain.models.remote.QuestionsRemoteModel
import com.paya.domain.models.remote.UserTest
import com.paya.domain.models.remote.UserTestRemoteModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface QuestionService {

	@GET("q/questions/")
	suspend fun getAllQuestions(@Header("Authorization") auth: String): ApiResponse<BaseModel<List<QuestionsRemoteModel>>>
	
	@POST("q/test/")
	suspend fun submitUserTest(
		@Header("Authorization") auth: String,
		@Body userTest: UserTest
	): ApiResponse<BaseModel<UserTestRemoteModel>>
	
}