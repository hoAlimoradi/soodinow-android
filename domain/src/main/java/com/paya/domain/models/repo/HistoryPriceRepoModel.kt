package com.paya.domain.models.repo

data class HistoryPriceRepoModel(
	val currentPage: Int,
	val priceHistory: List<PriceModel>
)

data class PriceModel(val title:String)