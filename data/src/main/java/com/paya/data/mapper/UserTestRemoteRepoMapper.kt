package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.UserTestRemoteModel
import com.paya.domain.models.repo.UserTestRepoModel
import javax.inject.Inject

class UserTestRemoteRepoMapper @Inject constructor(): Mapper<
		UserTestRemoteModel,
		UserTestRepoModel>{
	
	override fun map(param: UserTestRemoteModel): UserTestRepoModel {
		return UserTestRepoModel(
			param.score ?: 0f
		)
	}
	
}