package com.paya.domain.usecase.auth

import com.paya.domain.models.repo.PerLoginRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class PerLoginUseCase @Inject constructor(
	private val authRepository: AuthRepository
): UseCase<String, PerLoginRepoModel> {
	override suspend fun action(param: String): Resource<PerLoginRepoModel> {
		return authRepository.perLogin(
			param
		)
	}
}