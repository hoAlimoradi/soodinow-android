package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.CryptoPriceRemoteModel
import com.paya.domain.models.repo.CurrencyPriceRepoModel
import javax.inject.Inject
import kotlin.math.abs

class CryptoPriceRemoteMapper @Inject constructor(): Mapper<
		CryptoPriceRemoteModel,
		CurrencyPriceRepoModel>{
	
	override fun map(param: CryptoPriceRemoteModel): CurrencyPriceRepoModel {
		val nameMap = mapOf(
			"Bitcoin" to "بیتکوین",
			"Ethereum" to "اتریوم"
		)
		val priceChange = getPriceChange(param.percentChange)
		return CurrencyPriceRepoModel(
			name = nameMap[param.name],
			price = param.priceUsd.toLongOrNull(),
			changeStatus = priceChange?.let { getPriceStatus(it) },
			changePercent = priceChange?.let { abs(it) }
		)
	}
	
	private fun getPriceChange(priceChange: String): Float?{
		return priceChange.toFloatOrNull()
	}
	
	private fun getPriceStatus(priceChange: Float): String?{
		return when {
			priceChange > 0 -> {
				"+"
			}
			priceChange < 0 -> {
				"-"
			}
			else -> {
				null
			}
		}
	}
	
}