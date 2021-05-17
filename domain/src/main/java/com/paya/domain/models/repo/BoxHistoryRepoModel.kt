package com.paya.domain.models.repo

data class BoxHistoryRepoModel(
    val cardChart: LinearChartRepoModel,
    val mainChart: LinearChartRepoModel,
    val circleChart: List<CircleChartDataRepoModel>,
    val buyValue: LineChart,
    val percent: Float,
    val name: String
)

