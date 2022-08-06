package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.local.DeveloperNameDbModel
import com.paya.domain.models.repo.DeveloperNameRepoModel
import javax.inject.Inject

class DeveloperNameRepoEntityMapper @Inject constructor() : Mapper<
		DeveloperNameRepoModel?,
		DeveloperNameDbModel
		> {
	
	override fun map(param: DeveloperNameRepoModel?): DeveloperNameDbModel =
		DeveloperNameDbModel(
			first = param?.first?:"",
			last = param?.last?:""
		)
}