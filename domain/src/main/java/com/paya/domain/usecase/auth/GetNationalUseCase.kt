package com.paya.domain.usecase.auth

import com.paya.domain.models.local.NationalCodeModel
import com.paya.domain.models.repo.UserInfoRepoModel
import com.paya.domain.models.repo.LoginRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class GetNationalUseCase @Inject constructor(
	private val authRepository: AuthRepository
): UseCase<Unit, NationalCodeModel> {
	override suspend fun action(param: Unit): Resource<NationalCodeModel> {
		return authRepository.getNationalCode()
	}
}