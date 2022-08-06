package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.local.UserInfoDbModel
import com.paya.domain.models.repo.UserInfoRepoModel
import javax.inject.Inject

class AccessTokenRepoEntityMapper @Inject constructor() : Mapper<
		UserInfoRepoModel?,
		UserInfoDbModel
		> {
	
	override fun map(param: UserInfoRepoModel?): UserInfoDbModel {
		return UserInfoDbModel(
			isPasswordSet = param?.isPasswordSet ?: false,
			isHintShowed = param?.isHintShowed ?: false
		)
	}
	
}