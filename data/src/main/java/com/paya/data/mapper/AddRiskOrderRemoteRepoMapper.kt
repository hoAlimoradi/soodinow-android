package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.AddRiskOrderItem
import com.paya.domain.models.remote.AddRiskOrderRemoteModel
import com.paya.domain.models.remote.ExitAccountRemoteModel
import com.paya.domain.models.remote.IsInRiskListRemoteModel
import com.paya.domain.models.repo.*
import javax.inject.Inject

class AddRiskOrderRemoteRepoMapper @Inject constructor(): Mapper<
		AddRiskOrderItem,
		AddRiskOrderRepoItem>{
	
	override fun map(param: AddRiskOrderItem): AddRiskOrderRepoItem {
		return AddRiskOrderRepoItem(
			param.isin,
			param.id,
			param.orderId,
			param.stateId,
			param.state,
			param.statusCode,
			param.ruleAction
		)
	}
	
}