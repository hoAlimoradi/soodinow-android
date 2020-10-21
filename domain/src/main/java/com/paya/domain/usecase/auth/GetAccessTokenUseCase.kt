package com.paya.domain.usecase.auth

import com.paya.domain.models.repo.AccessTokenRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(
	private val authRepository: AuthRepository
): UseCase<Unit,AccessTokenRepoModel> {
	override suspend fun action(param: Unit): Resource<AccessTokenRepoModel> {
		return authRepository.getAccessToken()
	}
}