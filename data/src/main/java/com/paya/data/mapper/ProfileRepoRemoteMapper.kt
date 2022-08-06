package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.AccessTokenRemoteModel
import com.paya.domain.models.remote.ProfileBodyRemoteModel
import com.paya.domain.models.remote.ProfileRemoteModel
import com.paya.domain.models.repo.ProfileBodyRepoModel
import com.paya.domain.models.repo.ProfileRepoModel
import com.paya.domain.models.repo.UserInfoRepoModel
import javax.inject.Inject

class ProfileRepoRemoteMapper @Inject constructor() : Mapper<
		ProfileBodyRepoModel,
		ProfileBodyRemoteModel
		> {
	
	override fun map(param: ProfileBodyRepoModel): ProfileBodyRemoteModel {
		return ProfileBodyRemoteModel(
			param.firstName,
			param.lastName,
			param.phone,
			param.email,
			param.birthDay,
			param.bban,
			param.gender,
			param.state,
			param.city,
			param.address
		)
	}
	
}