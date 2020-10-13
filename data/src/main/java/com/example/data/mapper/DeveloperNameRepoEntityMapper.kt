package com.example.data.mapper

import com.example.common.Mapper
import com.example.domain.models.local.DeveloperNameDbModel
import com.example.domain.models.repo.DeveloperNameRepoModel
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