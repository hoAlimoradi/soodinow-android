package com.paya.domain.usecase.auth

import com.paya.domain.models.repo.SetPasswordRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
	private val authRepository: AuthRepository
): UseCase<Unit, Unit> {
	override suspend fun action(param: Unit): Resource<Unit> {
		return authRepository.clearToken()
	}
}