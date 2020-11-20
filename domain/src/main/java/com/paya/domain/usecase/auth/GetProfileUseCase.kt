package com.paya.domain.usecase.auth

import com.paya.domain.models.repo.ProfileBodyRepoModel
import com.paya.domain.models.repo.ProfileRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
	private val authRepository: AuthRepository
): UseCase<Unit, ProfileRepoModel> {
	override suspend fun action(param: Unit): Resource<ProfileRepoModel> {
		return authRepository.getProfile()
	}
}