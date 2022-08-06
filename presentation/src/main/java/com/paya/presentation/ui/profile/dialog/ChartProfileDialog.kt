package com.paya.presentation.ui.profile.dialog

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.tabs.TabLayout
import com.paya.domain.models.repo.ChartProfitRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseDialogFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.DialogChartProfileBinding
import com.paya.presentation.ui.custom.MyMarkerViewSmall
import com.paya.presentation.utils.observe
import com.paya.presentation.utils.setWidthPercent
import com.paya.presentation.utils.shared.EntryPoint
import com.paya.presentation.utils.shared.Point
import com.paya.presentation.viewmodel.ChartProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_chart_profile.*

@AndroidEntryPoint
class ChartProfileDialog : BaseDialogFragment<ChartProfileViewModel>() {
    private val viewModel: ChartProfileViewModel by viewModels()
    private  var mBinding: DialogChartProfileBinding? =null
    var boxId: Long = 0
    override val baseViewModel: BaseViewModel
        get() = viewModel

    override fun onStart() {
        super.onStart()
        setWidthPercent(90)
        dialog?.window?.let {
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DialogChartProfileBinding.inflate(
            inflater,
            container,
            false
        )
 return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.chartProfit, ::readyProfile)
        viewModel.getChartProfit(boxId)
        mBinding?.apply {
            closeBtn.setOnClickListener {
                dismissAllowingStateLoss()
            }
        }
    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }

    private fun readyProfile(resource: Resource<List<ChartProfitRepoModel>>) {
        if (resource.status == Status.SUCCESS) {
            resource.data?.let {
                if (it.isNotEmpty()) {
                    initTab(it)
                    setCurrentBoxData(it.size - 1)
                }
            }

        }
    }


    private fun setCurrentBoxData(index: Int) {
        if (viewModel.chartListModels.size > 0)
            setLineAccountChartData(
                chart,
                viewModel.chartListModels[index].mainChartPoints,
                chartColor = ContextCompat.getColor(
                    requireContext(),
                    R.color.japanese_laurel_green
                ),
                markerColor = ContextCompat.getColor(requireContext(), R.color.conifer_green),
                markerTitleColor = Color.WHITE,
                chartAlpha = 0,
                markerType = 0,
                touchEnabled = true,
                xList = viewModel.chartListModels[index].xListChart
            )

    }

    @SuppressLint("WrongConstant")
    private fun initTab(tabs: List<ChartProfitRepoModel>) {
        tabs.forEach {
            val tab = chartTabLayout.newTab()
            tab.text = it.name
            chartTabLayout.addTab(tab)
        }
        chartTabLayout.getTabAt(tabs.size - 1)?.select()
        chartTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            // TODO: 4/2/21 box history Number what?
            override fun onTabSelected(tab: TabLayout.Tab?) {
                setCurrentBoxData(chartTabLayout.selectedTabPosition)
            }

        })

    }

    fun setLineAccountChartData(
        chart: LineChart,
        data: List<Point>?,
        chartColor: Int = Color.WHITE,
        markerColor: Int = Color.WHITE,
        markerTitleColor: Int = Color.BLACK,
        markerType: Int = 1,
        chartAlpha: Int = 0,
        touchEnabled: Boolean = false,
        xList: List<String> = mutableListOf()
    ) {
        data ?: return
        // chart.setViewPortOffsets(0f, 0f, 0f, 0f)
        chart.setBackgroundColor(Color.TRANSPARENT)

        // no description text
        chart.description.isEnabled = false

        // enable touch gestures
        chart.setTouchEnabled(true)

        // enable scaling and dragging
        chart.isDragEnabled = false
        chart.setScaleEnabled(false)

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false)


        chart.setDrawGridBackground(false)
        chart.maxHighlightDistance = 300f
        chart.xAxis.isEnabled = xList.isNotEmpty()
        if (xList.isNotEmpty())
            chart.xAxis.apply {
                isEnabled = true
                setDrawGridLines(false)
                setLabelCount(xList.size, true)
                axisMinimum = 0f
                textSize = 8f
                labelRotationAngle = 90f
                textColor = Color.LTGRAY
                axisMaximum = xList.size.toFloat() - 1
                position = XAxis.XAxisPosition.BOTTOM
                valueFormatter =
                    IndexAxisValueFormatter(xList)

            }


        chart.axisLeft.apply {
            isEnabled = true
            setLabelCount(6, true)
            textColor = Color.LTGRAY
            setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
            setDrawGridLines(true)
            if (data.size == 1)
                axisMinimum = 0f
            textSize = 8f
            zeroLineColor = ContextCompat.getColor(chart.context, R.color.white_smoke)
            axisLineColor = ContextCompat.getColor(chart.context, R.color.white_smoke)
            gridColor = ContextCompat.getColor(chart.context, R.color.white_smoke)

        }




        chart.axisRight.isEnabled = false

        chart.marker = MyMarkerViewSmall(
            chart.context,
            chart,
            markerColor,
            markerTitleColor,
            markerType
        )


        setAccountData(chart, data, chartColor, chartAlpha)

        chart.legend.isEnabled = false

        chart.animateXY(1000, 1000)

        // don't forget to refresh the drawing
        chart.invalidate()

        val index = chart.data.dataSets[0].entryCount - 1
        if (index > -1) {
            val x = chart.data.dataSets[0].getEntryForIndex(index).x

            val y = chart.data.dataSets[0].getEntryForIndex(index).y

            chart.highlightValue(x, y, 0)

            chart.marker.refreshContent(
                chart.data.dataSets[0].getEntryForIndex(index),
                chart.getHighlightByTouchPoint(x, y)
            )
        }
    }

    private fun setAccountData(
        chart: LineChart,
        points: List<Point>,
        chartColor: Int = Color.WHITE,
        chartAlpha: Int = 0
    ) {
        val set1: LineDataSet

        val entries = points.map { EntryPoint(it.x, it.y, it.percent, it.price) }

        if (chart.data != null &&
            chart.data.dataSetCount > 0
        ) {
            set1 = chart.data.getDataSetByIndex(0) as LineDataSet
            set1.values = entries
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(entries, "DataSet 1")
//            set1.mode = LineDataSet.Mode.CUBIC_BEZIER
//            set1.cubicIntensity = 0.2f
            set1.setDrawFilled(true)
            set1.setDrawCircles(false)
            set1.lineWidth = 3f
            set1.circleRadius = 0f
            set1.setCircleColor(Color.TRANSPARENT)
            set1.color = chartColor
            set1.fillColor = chartColor
            set1.fillAlpha = chartAlpha
            set1.setDrawHorizontalHighlightIndicator(true)
            set1.fillFormatter =
                IFillFormatter { _, _ -> chart.axisLeft.axisMinimum }
            // create a data object with the data sets
            val data = LineData(set1)
            data.setValueTextSize(9f)
            data.setDrawValues(false)

            // set data
            chart.data = data
        }
    }
}