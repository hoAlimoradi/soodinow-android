package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.local.DeveloperNameDbModel
import com.paya.domain.models.repo.DeveloperNameRepoModel
import javax.inject.Inject

class DeveloperNameEntityRepoMapper @Inject constructor() : Mapper<
		DeveloperNameDbModel?,
		DeveloperNameRepoModel
		> {
	override fun map(param: DeveloperNameDbModel?): DeveloperNameRepoModel=
		DeveloperNameRepoModel(
			first = param?.first?:"",
			last = param?.last?:""
		)
}