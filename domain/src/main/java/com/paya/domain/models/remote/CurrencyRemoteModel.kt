package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

class CurrencyRemoteModel (
	@SerializedName("list")
	val prices: List<CurrencyPriceRemoteModel>
): NoObfuscate