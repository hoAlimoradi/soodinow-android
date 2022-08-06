package com.paya.domain.repository

import com.paya.domain.models.repo.AccountCardRepoModel
import com.paya.domain.models.repo.UserInfoRepoModel
import com.paya.domain.models.repo.RegisterRepoModel
import com.paya.domain.models.repo.SetPasswordRepoModel
import com.paya.domain.tools.Resource

interface AccountRepository {
	suspend fun getAccount(): Resource<AccountCardRepoModel>
	
}