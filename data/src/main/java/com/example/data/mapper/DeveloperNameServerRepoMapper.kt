package com.example.data.mapper

import com.example.common.Mapper
import com.example.domain.models.remote.DeveloperNameServerModel
import com.example.domain.models.repo.DeveloperNameRepoModel
import javax.inject.Inject

class DeveloperNameServerRepoMapper @Inject constructor(): Mapper<
		DeveloperNameServerModel,
		DeveloperNameRepoModel
		>{
	
	override fun map(param: DeveloperNameServerModel): DeveloperNameRepoModel =
		DeveloperNameRepoModel(
			first = param.first,
			last = param.last
		)
}