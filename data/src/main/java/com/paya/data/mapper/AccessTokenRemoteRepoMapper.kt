package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.AccessTokenRemoteModel
import com.paya.domain.models.repo.UserInfoRepoModel
import javax.inject.Inject

class AccessTokenRemoteRepoMapper @Inject constructor() : Mapper<
		AccessTokenRemoteModel,
		UserInfoRepoModel
		> {
	
	override fun map(param: AccessTokenRemoteModel): UserInfoRepoModel {
		return UserInfoRepoModel(param.accessToken ?: "")
	}
	
}