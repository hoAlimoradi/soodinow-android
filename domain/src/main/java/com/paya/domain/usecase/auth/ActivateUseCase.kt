package com.paya.domain.usecase.auth

import com.paya.domain.models.repo.ActivateRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class ActivateUseCase @Inject constructor(
	private val authRepository: AuthRepository
): UseCase<ActivateRepoModel, Any> {
	override suspend fun action(param: ActivateRepoModel): Resource<Any> {
		val resource =  authRepository.activate(
			param.username,
			param.phoneNumber,
			param.code
		)
		authRepository.setMobile(param.username)
		if (resource.status == Status.SUCCESS){
			resource.data?.accessToken?.let { authRepository.updateAccessToken(it) }
			resource.data?.refreshToken?.let { authRepository.updateRefreshToken(it) }
		}
		return resource
	}
}