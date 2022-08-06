package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.AccessTokenRemoteModel
import com.paya.domain.models.remote.GetAuthLinkRemoteModel
import com.paya.domain.models.repo.GetAuthLinkRepoModel
import com.paya.domain.models.repo.UserInfoRepoModel
import javax.inject.Inject

class GetAuthLinkRemoteRepoMapper @Inject constructor() : Mapper<
		GetAuthLinkRemoteModel,
		GetAuthLinkRepoModel
		> {
	
	override fun map(param: GetAuthLinkRemoteModel): GetAuthLinkRepoModel {
		return GetAuthLinkRepoModel(
			link = param.link ?: ""
		)
	}
	
}