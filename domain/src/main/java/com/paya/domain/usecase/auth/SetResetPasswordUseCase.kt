package com.paya.domain.usecase.auth

import com.paya.domain.models.repo.SetPasswordRepoModel
import com.paya.domain.models.repo.SetResetPasswordRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class SetResetPasswordUseCase @Inject constructor(
	private val authRepository: AuthRepository
): UseCase<String, SetResetPasswordRepoModel> {
	override suspend fun action(param: String): Resource<SetResetPasswordRepoModel> {
		val resource = authRepository.setResetPassword(param)
		if (resource.status == Status.SUCCESS){
			authRepository.updateIsPasswordSet(true)
		}
		return resource
	}
}