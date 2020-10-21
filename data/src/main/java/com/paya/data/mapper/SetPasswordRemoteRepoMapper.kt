package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.SetPasswordRemoteModel
import com.paya.domain.models.repo.SetPasswordRepoModel
import javax.inject.Inject

class SetPasswordRemoteRepoMapper @Inject constructor(): Mapper<
		SetPasswordRemoteModel,
		SetPasswordRepoModel
		> {
	override fun map(param: SetPasswordRemoteModel): SetPasswordRepoModel {
		return SetPasswordRepoModel(
			message = param.message
		)
	}
}