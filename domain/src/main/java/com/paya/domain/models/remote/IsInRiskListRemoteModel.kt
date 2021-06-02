package com.paya.domain.models.remote

import com.paya.domain.tools.NoObfuscate

data class IsInRiskListRemoteModel(
	val basket: List<BasketRemoteModel>?,
	val percent: PercentRemoteModel?
): NoObfuscate