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
			activeBox = param.activeBox.map {
				ActiveBoxRepo(
					it.id,
					it.type,
					it.subType,
					it.createAt,
					it.userName,
					it.price.toLong()
				)
			}
		)
	}
	
}