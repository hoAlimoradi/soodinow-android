package com.paya.domain.models.repo

data class AddRiskOrderRepoItem(
	val isin: String,
	val id: Long,
	val orderId: Long,
	val stateId: Long,
	val state: String,
	val statusCode: Int,
	val ruleAction: String
)