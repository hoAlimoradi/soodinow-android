package com.paya.data.repository

import com.paya.common.Mapper
import com.paya.data.network.remote_api.QuestionService
import com.paya.data.utils.getResourceFromApiResponse
import com.paya.domain.models.remote.QuestionsRemoteModel
import com.paya.domain.models.repo.QuestionsRepoModel
import com.paya.domain.repository.QuestionsRepository
import com.paya.domain.tools.Resource
import javax.inject.Inject

class QuestionsRepositoryImpl @Inject constructor(
	private val questionService: QuestionService,
	private val questionsRemoteRepoMapper: Mapper<QuestionsRemoteModel, QuestionsRepoModel>
): QuestionsRepository{
	
	override suspend fun getAllQuestions(): Resource<QuestionsRepoModel> {
		val questionsResponse = questionService.getAllQuestions()
		return getResourceFromApiResponse(questionsResponse){
			questionsRemoteRepoMapper.map(it.data)
		}
	}
	
}