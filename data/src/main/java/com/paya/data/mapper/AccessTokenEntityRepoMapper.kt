package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.local.AccessTokenDbModel
import com.paya.domain.models.remote.AccessTokenRemoteModel
import com.paya.domain.models.repo.AccessTokenRepoModel
import javax.inject.Inject

class AccessTokenEntityRepoMapper @Inject constructor() : Mapper<
		AccessTokenDbModel?,
		AccessTokenRepoModel
		> {
	
	override fun map(param: AccessTokenDbModel?): AccessTokenRepoModel {
		return AccessTokenRepoModel(
			accessToken = param?.accessToken ?: ""
		)
	}
	
}