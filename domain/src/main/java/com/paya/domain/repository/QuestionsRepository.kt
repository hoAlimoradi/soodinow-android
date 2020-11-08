package com.paya.domain.repository

import com.paya.domain.models.repo.QuestionsRepoModel
import com.paya.domain.tools.Resource

interface QuestionsRepository {
	suspend fun getAllQuestions(): Resource<ArrayList<QuestionsRepoModel>>
}