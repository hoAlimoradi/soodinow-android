package com.example.data.mapper

import com.example.common.Mapper
import com.example.domain.models.local.DeveloperNameDbModel
import com.example.domain.models.repo.DeveloperNameRepoModel
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