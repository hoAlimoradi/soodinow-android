package com.paya.domain.usecase.auth

import com.paya.domain.models.repo.ActivateRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class IsLoginUseCase @Inject constructor(
	private val authRepository: AuthRepository
): UseCase<Unit, Boolean> {
	override suspend fun action(param: Unit): Resource<Boolean> {
		return authRepository.isLogin()
	}
}