package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.AccessTokenRemoteModel
import com.paya.domain.models.repo.AccessTokenRepoModel

class AccessTokenRemoteRepoMapper : Mapper<
		AccessTokenRemoteModel,
		AccessTokenRepoModel
		> {
	
	override fun map(param: AccessTokenRemoteModel): AccessTokenRepoModel {
		return AccessTokenRepoModel(param.accessToken)
	}
	
}