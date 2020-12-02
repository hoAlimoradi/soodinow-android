package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.CurrencyPriceRemoteModel
import com.paya.domain.models.repo.CurrencyPriceRepoModel
import javax.inject.Inject

class CurrencyPriceRemoteRepoMapper @Inject constructor(): Mapper<
		CurrencyPriceRemoteModel,
		CurrencyPriceRepoModel>{
	
	override fun map(param: CurrencyPriceRemoteModel): CurrencyPriceRepoModel {
		return CurrencyPriceRepoModel(
			name = param.name,
			price = param.price,
			changeStatus = if (param.changeStatus != "0") param.changeStatus else null,
			changePercent = if (param.changeStatus != "0") param.changePercent else null
		)
	}
	
}