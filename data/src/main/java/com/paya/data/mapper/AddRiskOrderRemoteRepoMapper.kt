package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.AddRiskOrderItem
import com.paya.domain.models.remote.AddRiskOrderRemoteModel
import com.paya.domain.models.remote.ExitAccountRemoteModel
import com.paya.domain.models.remote.IsInRiskListRemoteModel
import com.paya.domain.models.repo.*
import javax.inject.Inject

class AddRiskOrderRemoteRepoMapper @Inject constructor(): Mapper<
		String,
		AddRiskOrderRepoItem>{
	
	override fun map(param: String): AddRiskOrderRepoItem {
		return AddRiskOrderRepoItem(param)
	}
	
}