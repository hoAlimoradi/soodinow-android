package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.ActivateRemoteModel
import com.paya.domain.models.remote.RegisterRemoteModel
import com.paya.domain.models.repo.ActivateRepoModel
import com.paya.domain.models.repo.RegisterRepoModel
import javax.inject.Inject

class ActivateRemoteRepoMapper @Inject constructor() : Mapper<
		ActivateRemoteModel,
		ActivateRepoModel
		> {
	override fun map(param: ActivateRemoteModel): ActivateRepoModel =
		ActivateRepoModel(
			username = param.username ?: "",
			code = param.code ?: ""
		)
}