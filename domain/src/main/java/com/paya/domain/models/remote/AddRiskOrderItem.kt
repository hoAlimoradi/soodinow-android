package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName

data class AddRiskOrderItem(
	val isin: String,
	val id: Long,
	@SerializedName("order_id")
	val orderId: Long,
	@SerializedName("state_id")
	val stateId: Long,
	val state: String,
	@SerializedName("status_code")
	val statusCode: Int,
	@SerializedName("rule_action")
	val ruleAction: String
)