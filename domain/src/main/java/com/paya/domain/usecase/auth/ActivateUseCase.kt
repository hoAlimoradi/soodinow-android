package com.paya.domain.usecase.auth

import com.paya.domain.models.repo.ActivateRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class ActivateUseCase @Inject constructor(
	private val authRepository: AuthRepository
): UseCase<ActivateRepoModel, ActivateRepoModel> {
	override suspend fun action(param: ActivateRepoModel): Resource<ActivateRepoModel> {
		return authRepository.activate(
			param.username,
			param.code
		)
	}
}