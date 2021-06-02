package com.paya.domain.models.repo

data class BoxHistoryRepoModel(
    val cardChart: LinearChartRepoModel,
    val efficiency:EfficiencyRepoModel,
    val mainChart: LinearChartRepoModel,
    val circleChart: List<CircleChartDataRepoModel>,
    val percent: Float,
    val name: String
)

data class EfficiencyRepoModel(
    var title: String,
    val percent: Float,
    val price: Double,
    val isProfitable: Boolean,
    var position: Int = 0,
) : Comparable<EfficiencyRepoModel> {
    override fun compareTo(other: EfficiencyRepoModel): Int {
        return this.position.compareTo(other.position)
    }

}

