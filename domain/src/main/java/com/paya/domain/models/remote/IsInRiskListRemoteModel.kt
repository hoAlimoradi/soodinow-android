package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class IsInRiskListRemoteModel(
	val basket: List<BasketRemoteModel>?,
	@SerializedName("soodinow_profit")
	val soodinowProfit: List<SoodinowProfitRemoteModel>?
): NoObfuscate

data class SoodinowProfitRemoteModel(
	val days:Int,
	val profit :Float,
	val name: String
): NoObfuscate