package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.AccessTokenRemoteModel
import com.paya.domain.models.remote.ChangePasswordRemoteBodyModel
import com.paya.domain.models.remote.ProfileBodyRemoteModel
import com.paya.domain.models.remote.ProfileRemoteModel
import com.paya.domain.models.repo.ChangePasswordRepoBodyModel
import com.paya.domain.models.repo.ProfileBodyRepoModel
import com.paya.domain.models.repo.ProfileRepoModel
import com.paya.domain.models.repo.UserInfoRepoModel
import javax.inject.Inject

class ChangePasswordRepoRemoteMapper @Inject constructor() : Mapper<
		ChangePasswordRepoBodyModel,
		ChangePasswordRemoteBodyModel
		> {
	
	override fun map(param: ChangePasswordRepoBodyModel): ChangePasswordRemoteBodyModel {
		return ChangePasswordRemoteBodyModel(
			param.oldPassword,
			param.password,
		)
	}
	
}