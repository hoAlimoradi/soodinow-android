package com.paya.domain.models.repo

data class LinearChartRepoModel (
	val data: List<LineChart>,
	val startDate: String,
	val endDate: String
)
data class LineChart(val price: Double, val date: String)