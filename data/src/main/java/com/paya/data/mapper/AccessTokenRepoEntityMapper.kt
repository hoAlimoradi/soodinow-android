package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.local.AccessTokenDbModel
import com.paya.domain.models.remote.AccessTokenRemoteModel
import com.paya.domain.models.repo.AccessTokenRepoModel
import javax.inject.Inject

class AccessTokenRepoEntityMapper @Inject constructor() : Mapper<
		AccessTokenRepoModel?,
		AccessTokenDbModel
		> {
	
	override fun map(param: AccessTokenRepoModel?): AccessTokenDbModel {
		return AccessTokenDbModel(
			accessToken = param?.accessToken ?: ""
		)
	}
	
}