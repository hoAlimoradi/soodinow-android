package com.paya.domain.usecase.auth

import com.paya.domain.models.repo.ResetPhoneVerifyBodyRepoModel
import com.paya.domain.models.repo.ResetPhoneVerifyRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class ResetPhoneVerifyUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : UseCase<ResetPhoneVerifyBodyRepoModel, ResetPhoneVerifyRepoModel> {
    override suspend fun action(param: ResetPhoneVerifyBodyRepoModel): Resource<ResetPhoneVerifyRepoModel> {
        return authRepository.resetPhoneVerify(param.phoneNumber, param.code)
    }
}