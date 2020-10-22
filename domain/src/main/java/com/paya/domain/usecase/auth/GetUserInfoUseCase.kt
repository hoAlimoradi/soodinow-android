package com.paya.domain.usecase.auth

import com.paya.domain.models.repo.UserInfoRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
	private val authRepository: AuthRepository
): UseCase<Unit,UserInfoRepoModel> {
	override suspend fun action(param: Unit): Resource<UserInfoRepoModel> {
		return authRepository.getUserInfo()
	}
}