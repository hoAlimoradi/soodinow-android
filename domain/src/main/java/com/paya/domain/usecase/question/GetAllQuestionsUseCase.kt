package com.paya.domain.usecase.question

import com.paya.domain.models.repo.QuestionsRepoModel
import com.paya.domain.repository.QuestionsRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class GetAllQuestionsUseCase @Inject constructor(
	private val questionsRepository: QuestionsRepository
): UseCase<Unit, ArrayList<QuestionsRepoModel>>{
	override suspend fun action(param: Unit): Resource<ArrayList<QuestionsRepoModel>> {
		return questionsRepository.getAllQuestions()
	}
	
}