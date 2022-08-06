package com.paya.domain.usecase.question

import com.paya.domain.models.remote.UserTestBody
import com.paya.domain.models.repo.UserTestRepoModel
import com.paya.domain.repository.QuestionsRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class SubmitTestUseCase @Inject constructor(
	private val questionsRepository: QuestionsRepository
): UseCase<@kotlin.jvm.JvmSuppressWildcards List<UserTestBody>, UserTestRepoModel>{
	
	override suspend fun action(param: List<UserTestBody>): Resource<UserTestRepoModel> {
		return questionsRepository.submitUserTest(param)
	}
	
}