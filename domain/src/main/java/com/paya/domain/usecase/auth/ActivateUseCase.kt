package com.paya.domain.usecase.auth

import com.paya.domain.models.repo.AccessTokenRepoModel
import com.paya.domain.models.repo.ActivateRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class ActivateUseCase @Inject constructor(
	private val authRepository: AuthRepository
): UseCase<ActivateRepoModel, AccessTokenRepoModel> {
	override suspend fun action(param: ActivateRepoModel): Resource<AccessTokenRepoModel> {
		val resource =  authRepository.activate(
			param.username,
			param.code
		)
		if (resource.status == Status.SUCCESS){
			resource.data?.let { authRepository.updateAccessToken(it) }
		}
		return resource
	}
}