package com.paya.domain.usecase.auth

import com.paya.domain.models.repo.ProfileBodyRepoModel
import com.paya.domain.models.repo.ProfileRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
	private val authRepository: AuthRepository
): UseCase<ProfileBodyRepoModel, ProfileRepoModel> {
	override suspend fun action(param: ProfileBodyRepoModel): Resource<ProfileRepoModel> {
		return authRepository.updateProfile(param)
	}
}