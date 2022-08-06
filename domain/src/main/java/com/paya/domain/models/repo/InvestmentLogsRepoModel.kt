package com.paya.domain.models.repo

data class InvestmentLogsRepoModel(
	val count: Int,
	val next: String,
	val previous: String,
	val results: List<Any>,
)
data class InvestmentLogsModel(
	val id: Long,
	val startPrice: Long,
	val finalPrice: Long,
	val type: String,
	val investmentType: String,
	val investmentSubType: String,
	val user: String,
	val state: String,
	val trackingNumber: String,
	val hasError: Boolean,
	val errorDescription: String,
	val createdAt: String,
	val updatedAt: String,
	val description: String,
)

data class InvestmentHeaderDate(val date: String)