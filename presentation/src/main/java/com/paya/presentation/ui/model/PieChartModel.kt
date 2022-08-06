package com.paya.presentation.ui.model

import com.github.mikephil.charting.data.PieEntry
import com.paya.presentation.ui.adapter.chartLablel.ChartLabelAdapter

data class PieChartModel(
    val entries: ArrayList<PieEntry> = ArrayList(),
    val chartLabels: MutableList<ChartLabelAdapter.ChartLabelModel> = mutableListOf(),
    val chartColor: MutableList<Int> = mutableListOf()
)