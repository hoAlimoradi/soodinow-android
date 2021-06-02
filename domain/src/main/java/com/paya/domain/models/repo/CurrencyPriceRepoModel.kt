package com.paya.domain.models.repo

data class CurrencyPriceRepoModel(
	val name: String?,
	var price: Float?,
	val changeStatus: String? = "0",
	val changePercent: Float? = null,
	val currency: Currency
)

enum class Currency(val persianTitle:String) {
	Rial("ریال"),
	Dollar("دلار"),
	Bourse("ریال")
}