package com.paya.data.mapper

import com.paya.common.Mapper

import com.paya.domain.models.repo.*
import javax.inject.Inject

class AddRiskOrderRemoteRepoMapper @Inject constructor(): Mapper<
		String,
		AddRiskOrderRepoItem>{
	
	override fun map(param: String): AddRiskOrderRepoItem {
		return AddRiskOrderRepoItem("")
	}
	
}