package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.CommonMarktDataPriceRemoteModel
import com.paya.domain.models.repo.Currency
import com.paya.domain.models.repo.CurrencyPriceRepoModel
import javax.inject.Inject

class CommonMarktDataRemoteMapper @Inject constructor() : Mapper<
		@JvmSuppressWildcards List<CommonMarktDataPriceRemoteModel>,
		@JvmSuppressWildcards List<CurrencyPriceRepoModel>> {

	override fun map(param: List<CommonMarktDataPriceRemoteModel>): List<CurrencyPriceRepoModel> {
		val list = mutableListOf<CurrencyPriceRepoModel>()
		param.forEach lit@{
			if (isRemoveItem(it.slug ?: ""))
				return@lit
			val priceChange = it.percentChange ?: 0f
			list.add(
				CurrencyPriceRepoModel(
					name = it.name ?: "",
					price = it.priceUsd ?: 0f,
					changeStatus = getPriceStatus(priceChange),
					changePercent = priceChange * 100,
					currency = getCurrency(it.slug ?: "")
				)
			)
		}
		return list
	}


	private fun getPriceStatus(priceChange: Float): String? {
		return when {
			priceChange > 0 -> {
				"+"
			}
			priceChange < 0 -> {
				"-"
			}
			else -> {
				"+"
			}
		}
	}

	private fun isRemoveItem(slug: String): Boolean {
		if (slug.isEmpty())
			return false
		return when (slug) {
			"silver",
			"platinum",
			"palladium",
			"sana_buy_usd",
			"sana_buy_eur",
			"sana_buy_aed",
			"sana_sell_usd",
			"sana_sell_eur",
			"sana_sell_aed",
			"geram24",
			"oil",
			"oil_brent" -> true
			else -> false
		}
	}

	private fun getCurrency(slug: String): Currency {
		return when (slug) {
			"ons",
			"oil_opec" -> Currency.Dollar
			"bourse" -> Currency.Bourse
			else -> Currency.Rial
		}
	}

}