package com.paya.data.repository

import com.paya.common.Mapper
import com.paya.data.network.remote_api.QuestionService
import com.paya.data.utils.getResourceFromApiResponse
import com.paya.domain.models.remote.QuestionsRemoteModel
import com.paya.domain.models.remote.UserTest
import com.paya.domain.models.remote.UserTestBody
import com.paya.domain.models.remote.UserTestRemoteModel
import com.paya.domain.models.repo.QuestionsRepoModel
import com.paya.domain.models.repo.UserTestRepoModel
import com.paya.domain.repository.QuestionsRepository
import com.paya.domain.tools.Resource
import javax.inject.Inject

class QuestionsRepositoryImpl @Inject constructor(
	private val questionService: QuestionService,
	private val questionsRemoteRepoMapper: Mapper<QuestionsRemoteModel, QuestionsRepoModel>,
	private val userTestRemoteRepoMapper: Mapper<UserTestRemoteModel, UserTestRepoModel>
): QuestionsRepository{
	
	override suspend fun getAllQuestions(): Resource<ArrayList<QuestionsRepoModel>> {
		val questionsResponse = questionService.getAllQuestions()
		return getResourceFromApiResponse(questionsResponse){
			it.data.map { questionsRemoteModel ->
				questionsRemoteRepoMapper.map(questionsRemoteModel)
			} as ArrayList
		}
	}
	
	override suspend fun submitUserTest(userTest: List<UserTestBody>): Resource<UserTestRepoModel> {
		return getResourceFromApiResponse(questionService.submitUserTest(UserTest(userTest))){
			userTestRemoteRepoMapper.map(it.data)
		}
	}
	
}