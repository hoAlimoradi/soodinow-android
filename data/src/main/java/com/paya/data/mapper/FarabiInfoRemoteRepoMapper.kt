package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.ActivateRemoteModel
import com.paya.domain.models.remote.RegisterRemoteModel
import com.paya.domain.models.repo.ActivateRepoModel
import com.paya.domain.models.repo.FarabiInfoRepoModel
import com.paya.domain.models.repo.RegisterRepoModel
import javax.inject.Inject

class FarabiInfoRemoteRepoMapper @Inject constructor() : Mapper<
		String,
		FarabiInfoRepoModel
		> {
	override fun map(param: String): FarabiInfoRepoModel =
		FarabiInfoRepoModel(
			 param
		)
}