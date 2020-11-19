package com.paya.domain.models.remote

data class IsInRiskListRemoteModel(
	val basket: List<BasketRemoteModel>,
	val percent: PercentRemoteModel
)