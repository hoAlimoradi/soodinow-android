package com.paya.domain.usecase.auth

import com.paya.domain.models.repo.RegisterRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
	private val authRepository: AuthRepository
): UseCase<String, RegisterRepoModel>{
	override suspend fun action(param: String): Resource<RegisterRepoModel> {
		return authRepository.register(param)
	}
}