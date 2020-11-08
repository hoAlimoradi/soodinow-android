package com.paya.data.repository

import com.paya.common.Mapper
import com.paya.data.network.remote_api.AccountService
import com.paya.data.utils.getResourceFromApiResponse
import com.paya.domain.models.remote.AccountCardRemoteModel
import com.paya.domain.models.repo.AccountCardRepoModel
import com.paya.domain.repository.AccountRepository
import com.paya.domain.tools.Resource
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
	private val accountApi: AccountService,
	private val getAccountMapperRemoteRepo: Mapper<AccountCardRemoteModel,AccountCardRepoModel>,
) : AccountRepository {
	
	override suspend fun getAccount(): Resource<AccountCardRepoModel> {
		val accountCardApiResponse = accountApi.getAccount()
		return getResourceFromApiResponse(accountCardApiResponse){
			getAccountMapperRemoteRepo.map(it.data)
		}
	}
	
	
}