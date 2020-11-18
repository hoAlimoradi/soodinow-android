package com.paya.domain.models.repo

data class IsInRiskListRepoModel(
	val basket: List<BasketRepoModel>,
	val percent: PercentRepoModel
)