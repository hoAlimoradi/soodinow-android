package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.RegisterRemoteModel
import com.paya.domain.models.repo.RegisterRepoModel
import javax.inject.Inject

class RegisterRemoteRepoMapper @Inject constructor() : Mapper<
		RegisterRemoteModel,
		RegisterRepoModel
		> {
	override fun map(param: RegisterRemoteModel): RegisterRepoModel =
		RegisterRepoModel(
			username = param.username ?: ""
		)
}