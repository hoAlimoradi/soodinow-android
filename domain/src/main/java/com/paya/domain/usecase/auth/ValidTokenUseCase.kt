package com.paya.domain.usecase.auth

import com.paya.domain.models.repo.LoginRepoModel
import com.paya.domain.models.repo.ValidTokenRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class ValidTokenUseCase @Inject constructor(
	private val authRepository: AuthRepository
): UseCase<Unit, ValidTokenRepoModel> {
	override suspend fun action(param: Unit): Resource<ValidTokenRepoModel> {
		return authRepository.validToken()
	}
}