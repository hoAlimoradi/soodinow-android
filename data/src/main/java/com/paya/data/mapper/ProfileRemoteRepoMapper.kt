package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.ProfileRemoteModel
import com.paya.domain.models.repo.ProfileRepoModel
import javax.inject.Inject

class ProfileRemoteRepoMapper @Inject constructor() : Mapper<
		ProfileRemoteModel,
		ProfileRepoModel
		> {
	
	override fun map(param: ProfileRemoteModel): ProfileRepoModel {
		return ProfileRepoModel(
			param.id,
			param.name ?: "",
			param.personalCode ?: "",
			param.birthDay ?: "",
			param.bban ?: "",
			param.complete,
			param.mobile,
			param.gender,
			param.state,
			param.city,
			param.address
		)
	}
	
}