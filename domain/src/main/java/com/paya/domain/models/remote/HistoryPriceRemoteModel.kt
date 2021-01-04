package com.paya.domain.models.remote

data class HistoryPriceRemoteModel(
	val priceHistory: List<PriceModel>
)

data class PriceModel(val title:String)