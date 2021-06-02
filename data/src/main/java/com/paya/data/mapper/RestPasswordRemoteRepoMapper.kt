package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.RegisterRemoteModel
import com.paya.domain.models.remote.ResetPasswordRemoteModel
import com.paya.domain.models.repo.RegisterRepoModel
import com.paya.domain.models.repo.ResetPasswordRepoModel
import javax.inject.Inject

class RestPasswordRemoteRepoMapper @Inject constructor() : Mapper<
		ResetPasswordRemoteModel,
		ResetPasswordRepoModel
		> {
	override fun map(param: ResetPasswordRemoteModel): ResetPasswordRepoModel =
		ResetPasswordRepoModel(
			username = param.username ?: ""
		)
}