package com.paya.domain.usecase.auth

import com.paya.domain.models.repo.ActivateRepoModel
import com.paya.domain.models.repo.ActivateResetPasswordRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class ActivateResetPasswordUseCase @Inject constructor(
	private val authRepository: AuthRepository
): UseCase<ActivateResetPasswordRepoModel, Any> {
	override suspend fun action(param: ActivateResetPasswordRepoModel): Resource<Any> {
		val resource =  authRepository.activateResetPassword(
			param.username,
			param.code
		)
		authRepository.setMobile(param.username)
		if (resource.status == Status.SUCCESS){
			resource.data?.accessToken?.let { authRepository.updateAccessToken(it) }
		}
		return resource
	}
}