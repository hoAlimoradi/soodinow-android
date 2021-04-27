package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName


data class InvestmentLogsRemoteModel(
	val count: Int,
	val next: String?,
	val previous: String?,
	val results: List<InvestmentLogsModel>,
)
data class InvestmentLogsModel(
	@SerializedName("id")
	val id: Long,
	@SerializedName("start_price")
	val startPrice: Long,
	@SerializedName("final_price")
	val finalPrice: Long,
	@SerializedName("type")
	val type: String,
	@SerializedName("investment_type")
	val investmentType: String,
	@SerializedName("investment_sub_type")
	val investmentSubType: String?,
	@SerializedName("user")
	val user: String,
	@SerializedName("state")
	val state: String,
	@SerializedName("tracking_number")
	val trackingNumber: String,
	@SerializedName("has_error")
	val hasError: Boolean,
	@SerializedName("error_description")
	val errorDescription: String?,
	@SerializedName("created_at")
	val createdAt: String,
	@SerializedName("updated_at")
	val updatedAt: String,
)