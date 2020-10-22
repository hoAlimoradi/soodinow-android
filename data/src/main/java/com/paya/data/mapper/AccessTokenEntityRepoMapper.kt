package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.local.UserInfoDbModel
import com.paya.domain.models.repo.UserInfoRepoModel
import javax.inject.Inject

class AccessTokenEntityRepoMapper @Inject constructor() : Mapper<
		UserInfoDbModel?,
		UserInfoRepoModel
		> {
	
	override fun map(param: UserInfoDbModel?): UserInfoRepoModel {
		return UserInfoRepoModel(
			accessToken = param?.accessToken ?: "",
			isPasswordSet = param?.isPasswordSet ?: false,
			isHintShowed = param?.isHintShowed ?: false
		)
	}
	
}