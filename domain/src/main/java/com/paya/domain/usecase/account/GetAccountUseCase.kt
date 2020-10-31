package com.paya.domain.usecase.account

import com.paya.domain.models.repo.AccountCardRepoModel
import com.paya.domain.models.repo.ActivateRepoModel
import com.paya.domain.repository.AccountRepository
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class GetAccountUseCase @Inject constructor(
	private val accountRepository: AccountRepository
) : UseCase<Unit,AccountCardRepoModel> {
	override suspend fun action(param: Unit): Resource<AccountCardRepoModel> {
		return accountRepository.getAccount()
	}
}