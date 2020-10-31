package com.paya.data.repository

import com.paya.common.Mapper
import com.paya.data.database.userInfo.UserInfoDbApi
import com.paya.data.network.apiresponse.ApiEmptyResponse
import com.paya.data.network.apiresponse.ApiErrorResponse
import com.paya.data.network.apiresponse.ApiSuccessResponse
import com.paya.data.network.remote_api.AccountService
import com.paya.data.network.remote_api.AuthService
import com.paya.domain.models.local.UserInfoDbModel
import com.paya.domain.models.remote.AccessTokenRemoteModel
import com.paya.domain.models.remote.AccountCardRemoteModel
import com.paya.domain.models.remote.RegisterRemoteModel
import com.paya.domain.models.remote.SetPasswordRemoteModel
import com.paya.domain.models.repo.AccountCardRepoModel
import com.paya.domain.models.repo.UserInfoRepoModel
import com.paya.domain.models.repo.RegisterRepoModel
import com.paya.domain.models.repo.SetPasswordRepoModel
import com.paya.domain.repository.AccountRepository
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import java.lang.Exception
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
	private val accountApi: AccountService,
	private val getAccountMapperRemoteRepo: Mapper<AccountCardRemoteModel,AccountCardRepoModel>,
) : AccountRepository {
	
	override suspend fun getAccount(): Resource<AccountCardRepoModel> {
		return when (val accountCardModel = accountApi.getAccount()) {
			is ApiEmptyResponse -> Resource.success(null)
			is ApiSuccessResponse -> Resource.success(getAccountMapperRemoteRepo.map(accountCardModel.body.data))
			is ApiErrorResponse -> Resource.error(accountCardModel.errorMessage,null)
		}
	}
	
	
}