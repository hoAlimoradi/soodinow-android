package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.ProfileExtraRemoteModel
import com.paya.domain.models.remote.ProfileRemoteModel
import com.paya.domain.models.repo.ProfileExtraRepoModel
import com.paya.domain.models.repo.ProfileRepoModel
import javax.inject.Inject

class ProfileExtraRemoteRepoMapper @Inject constructor() : Mapper<
		ProfileExtraRemoteModel,
		ProfileExtraRepoModel
		> {
	
	override fun map(param: ProfileExtraRemoteModel): ProfileExtraRepoModel {
		return ProfileExtraRepoModel(
			param.firstName?.let { if (it.isEmpty()) "" else it[0] }?:"",
			param.lastName ?.let { if (it.isEmpty()) "" else it[0] }?:"",
			param.phone ?.let { if (it.isEmpty()) "" else it[0] }?:"",
			param.email ?.let { if (it.isEmpty()) "" else it[0] }?:"",
			param.personalCode ?.let { if (it.isEmpty()) "" else it[0] }?:"",
			param.birthDay ?.let { if (it.isEmpty()) "" else it[0] }?:"",
			param.bban ?.let { if (it.isEmpty()) "" else it[0] }?:"",
			param.mobile?.let { if (it.isEmpty()) "" else it[0] }?:"",
			param.gender ?.let { if (it.isEmpty()) "" else it[0] }?:"",
			param.state ?.let { if (it.isEmpty()) "" else it[0] }?:"",
			param.city ?.let { if (it.isEmpty()) "" else it[0] }?:"",
			param.address ?.let { if (it.isEmpty()) "" else it[0] }?:"",
			param.company ?.let { if (it.isEmpty()) "" else it[0] }?:"",
		)
	}
	
}