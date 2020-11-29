package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.ExitAccountRemoteModel
import com.paya.domain.models.remote.IsInRiskListRemoteModel
import com.paya.domain.models.repo.*
import javax.inject.Inject

class ExitAccountRemoteRepoMapper @Inject constructor(): Mapper<
		ExitAccountRemoteModel,
		ExitAccountRepoModel>{
	
	override fun map(param: ExitAccountRemoteModel): ExitAccountRepoModel {
		return ExitAccountRepoModel(
			existing = param.existing,
			activeBoxId = listOf(param.activeBoxId, param.activeBoxId, param.activeBoxId)
		)
	}
	
}