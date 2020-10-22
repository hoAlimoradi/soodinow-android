package com.paya.domain.usecase.auth

import com.paya.domain.models.repo.UserInfoRepoModel
import com.paya.domain.models.repo.LoginRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class LoginUseCase @Inject constructor(
	private val authRepository: AuthRepository
): UseCase<LoginRepoModel, Any> {
	override suspend fun action(param: LoginRepoModel): Resource<Any> {
		val resource =  authRepository.activate(
			param.username,
			param.password
		)
		if (resource.status == Status.SUCCESS){
			resource.data?.accessToken?.let { authRepository.updateAccessToken(it) }
		}
		return resource
	}
}