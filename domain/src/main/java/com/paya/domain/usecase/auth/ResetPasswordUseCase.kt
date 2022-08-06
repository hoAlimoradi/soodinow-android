package com.paya.domain.usecase.auth

import com.paya.domain.models.repo.RegisterRepoModel
import com.paya.domain.models.repo.ResetPasswordRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
	private val authRepository: AuthRepository
): UseCase<String, ResetPasswordRepoModel>{
	override suspend fun action(param: String): Resource<ResetPasswordRepoModel> {
		return authRepository.resetPassword(param)
	}
}