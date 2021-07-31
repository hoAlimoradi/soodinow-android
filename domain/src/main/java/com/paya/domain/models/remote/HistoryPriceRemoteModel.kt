package com.paya.domain.models.remote

import com.paya.domain.tools.NoObfuscate

data class HistoryPriceRemoteModel(
	val priceHistory: List<PriceModel>?
): NoObfuscate

data class PriceModel(val title:String): NoObfuscate

