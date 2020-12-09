package com.paya.domain.repository

import com.paya.domain.models.remote.UserTestBody
import com.paya.domain.models.repo.QuestionsRepoModel
import com.paya.domain.models.repo.UserTestRepoModel
import com.paya.domain.tools.Resource

interface QuestionsRepository {
	suspend fun getAllQuestions(): Resource<ArrayList<QuestionsRepoModel>>
	suspend fun submitUserTest(userTest: List<UserTestBody>): Resource<UserTestRepoModel>
}