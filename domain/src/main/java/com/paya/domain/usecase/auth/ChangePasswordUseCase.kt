package com.paya.domain.usecase.auth

import com.paya.domain.models.repo.ChangePasswordRepoBodyModel
import com.paya.domain.models.repo.ChangePasswordRepoModel
import com.paya.domain.models.repo.ProfileBodyRepoModel
import com.paya.domain.models.repo.ProfileRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
	private val authRepository: AuthRepository
): UseCase<ChangePasswordRepoBodyModel, ChangePasswordRepoModel> {
	override suspend fun action(param: ChangePasswordRepoBodyModel): Resource<ChangePasswordRepoModel> {
		return authRepository.changePassword(param)
	}
}