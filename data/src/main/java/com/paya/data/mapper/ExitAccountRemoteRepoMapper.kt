package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.ExitAccountRemoteModel
import com.paya.domain.models.repo.ActiveBoxRepo
import com.paya.domain.models.repo.EfficiencyRepoModel
import com.paya.domain.models.repo.ExitAccountRepoModel
import javax.inject.Inject

class ExitAccountRemoteRepoMapper @Inject constructor(): Mapper<
		ExitAccountRemoteModel,
		ExitAccountRepoModel>{
	
	override fun map(param: ExitAccountRemoteModel): ExitAccountRepoModel {
		return ExitAccountRepoModel(
			existing = param.existing,
			activeBox = param.activeBox?.let { activeBox ->
				activeBox.map {
					ActiveBoxRepo(
						it.id ?: 0,
						it.type ?: "",
						it.subType ?: "",
						it.createAt ?: "",
						it.userName ?: "",
						it.price?.toLong() ?: 0,
						it.userProfit?.map { userProfit ->
							EfficiencyRepoModel(
								userProfit.name ?: "",
								userProfit.soodinowProfit ?: 0f,
								userProfit.totalProfit ?: 0.0,
								userProfit.soodinowProfit ?: 0f >= 0
							)
						} ?: emptyList<EfficiencyRepoModel>()
					)
				}
			} ?: emptyList()
		)
	}
	
}