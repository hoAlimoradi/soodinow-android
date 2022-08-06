package com.paya.domain.usecase.auth

import com.paya.domain.models.repo.UserInfoRepoModel
import com.paya.domain.models.repo.LoginRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class GetMobileUseCase @Inject constructor(
	private val authRepository: AuthRepository
): UseCase<Unit, String> {
	override suspend fun action(param: Unit): Resource<String> {
		
		return authRepository.getMobile()
	}
}