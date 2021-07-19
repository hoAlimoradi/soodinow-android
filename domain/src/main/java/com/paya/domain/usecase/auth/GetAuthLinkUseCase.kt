package com.paya.domain.usecase.auth

import com.paya.domain.models.repo.GetAuthLinkRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class GetAuthLinkUseCase @Inject constructor(
	private val authRepository: AuthRepository
): UseCase<String, GetAuthLinkRepoModel> {
	override suspend fun action(param: String): Resource<GetAuthLinkRepoModel> {
		return authRepository.getAuthLink(param)
	}
}