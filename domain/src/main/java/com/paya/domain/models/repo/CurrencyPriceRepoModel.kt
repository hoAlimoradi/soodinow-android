package com.paya.domain.models.repo

data class CurrencyPriceRepoModel(
	val name: String?,
	var price: Long?,
	val changeStatus: String? = null,
	val changePercent: Float? = null
)