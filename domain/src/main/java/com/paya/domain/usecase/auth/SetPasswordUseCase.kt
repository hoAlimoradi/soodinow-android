package com.paya.domain.usecase.auth

import com.paya.domain.models.repo.SetPasswordRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class SetPasswordUseCase @Inject constructor(
	private val authRepository: AuthRepository
): UseCase<String, SetPasswordRepoModel> {
	override suspend fun action(param: String): Resource<SetPasswordRepoModel> {
		return authRepository.setPassword(param)
	}
}