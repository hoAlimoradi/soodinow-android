package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.DeveloperNameServerModel
import com.paya.domain.models.repo.DeveloperNameRepoModel
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