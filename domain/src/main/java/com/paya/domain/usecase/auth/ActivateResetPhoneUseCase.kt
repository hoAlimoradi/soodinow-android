package com.paya.domain.usecase.auth

import com.paya.domain.models.repo.ActivateResetPhoneBodyRepoModel
import com.paya.domain.models.repo.ActivateResetPhoneRepoModel
import com.paya.domain.models.repo.ResetPhoneVerifyBodyRepoModel
import com.paya.domain.models.repo.ResetPhoneVerifyRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class ActivateResetPhoneUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : UseCase<ActivateResetPhoneBodyRepoModel, ActivateResetPhoneRepoModel> {
    override suspend fun action(param: ActivateResetPhoneBodyRepoModel): Resource<ActivateResetPhoneRepoModel> {
        return authRepository.resetPhoneActivate(param.phoneNumber, param.code)
    }
}