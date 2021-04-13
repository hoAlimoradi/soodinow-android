package com.paya.domain.models.repo

data class IsInRiskListRepoModel(
	val chart: List<Float>,
	val basket: List<BasketRepoModel>,
	val percent: PercentRepoModel
)