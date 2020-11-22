package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.AddRiskOrderRemoteBodyModel
import com.paya.domain.models.remote.AddRiskOrderRemoteModel
import com.paya.domain.models.remote.ExitAccountRemoteModel
import com.paya.domain.models.remote.IsInRiskListRemoteModel
import com.paya.domain.models.repo.*
import javax.inject.Inject

class AddRiskOrderRepoRemoteMapper @Inject constructor(): Mapper<
		AddRiskOrderRepoBodyModel,
		AddRiskOrderRemoteBodyModel>{
	
	override fun map(param: AddRiskOrderRepoBodyModel): AddRiskOrderRemoteBodyModel {
		return AddRiskOrderRemoteBodyModel(
			param.type,
			param.price
		)
	}
	
}