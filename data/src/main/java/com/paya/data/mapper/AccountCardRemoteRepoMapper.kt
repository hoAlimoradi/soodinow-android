package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.AccessTokenRemoteModel
import com.paya.domain.models.remote.AccountCardRemoteModel
import com.paya.domain.models.repo.AccountCardRepoModel
import com.paya.domain.models.repo.UserInfoRepoModel
import javax.inject.Inject

class AccountCardRemoteRepoMapper @Inject constructor() : Mapper<
		AccountCardRemoteModel,
		AccountCardRepoModel
		> {
	
	override fun map(param: AccountCardRemoteModel): AccountCardRepoModel {
		return AccountCardRepoModel(param.number)
	}
	
}