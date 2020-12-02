package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName

class CurrencyRemoteModel (
	@SerializedName("list")
	val prices: List<CurrencyPriceRemoteModel>
)