package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.ChangePasswordRemoteModel
import com.paya.domain.models.remote.ProfileRemoteModel
import com.paya.domain.models.repo.ChangePasswordRepoModel
import com.paya.domain.models.repo.ProfileRepoModel
import javax.inject.Inject

class ChangePasswordRemoteRepoMapper @Inject constructor() : Mapper<
		ChangePasswordRemoteModel,
		ChangePasswordRepoModel
		> {
	
	override fun map(param: ChangePasswordRemoteModel): ChangePasswordRepoModel {
		return ChangePasswordRepoModel(
			param.message,
		)
	}
	
}