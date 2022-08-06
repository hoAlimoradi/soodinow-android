package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.repo.ActivateRepoModel
import javax.inject.Inject

class ActivateRemoteRepoMapper @Inject constructor() : Mapper<
		String,
		ActivateRepoModel
		> {
	override fun map(param: String): ActivateRepoModel =
		ActivateRepoModel(
			username = "",
			phoneNumber = "",
			code = ""
		)
}