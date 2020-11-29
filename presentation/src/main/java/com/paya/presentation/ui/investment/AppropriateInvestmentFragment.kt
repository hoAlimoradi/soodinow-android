package com.paya.presentation.ui.investment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import com.paya.domain.models.repo.BasketRepoModel
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentAppropriateInvestmentBinding
import com.paya.presentation.ui.investment.adapter.ChartLabelAdapter
import com.paya.presentation.ui.investment.adapter.ChartLabelModel
import kotlinx.android.synthetic.main.fragment_appropriate_investment.*
import java.util.*

class AppropriateInvestmentFragment : Fragment() {
	
	val chartLabels = listOf(
		ChartLabelModel("بذر","#008C23"),
		ChartLabelModel("کاریز","#A9CFA6"),
		ChartLabelModel("افق ملت ","#035058"),
		ChartLabelModel("آگاه","#62B366")
	)
	
//	val basket = mutableListOf<BasketRepoModel>()
	
	
	val pieChartDataList = mutableListOf<PieChartData>()
	
	data class PieChartData(
		val percent: Float,
		val title: String
	)
	
	private lateinit var mBinding: FragmentAppropriateInvestmentBinding
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = DataBindingUtil.inflate(
			inflater,
			R.layout.fragment_appropriate_investment,
			container,
			false
		)
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		setup()
	}
	
	fun setup() {
		setupPieChart()
		setupChartLabelRecyclerView()
	}
	
	private fun setupChartLabelRecyclerView() {
		val layoutManager = GridLayoutManager(requireContext(),2,RecyclerView.VERTICAL,true)
		layoutManager.reverseLayout = true
		
		val cl = mutableListOf<ChartLabelModel>()
		if (pieChartDataList.isNotEmpty()) {
			pieChartDataList.forEachIndexed { index,pieChartData ->
				cl.add(
					ChartLabelModel(
						pieChartData.title,
						chartLabels[index % chartLabels.size].labelColor
					)
				)
			}
		} else {
			chartLabels.forEachIndexed { index,chartLabelModel ->
				cl.add(
					ChartLabelModel(
						chartLabelModel.labelName,
						chartLabels[index % chartLabels.size].labelColor
					)
				)
			}
		}
		val adapter = ChartLabelAdapter(cl) {
			val x = chart.data.dataSets[0].getEntryForIndex(it).x
			val y = chart.data.dataSets[0].getEntryForIndex(it).y
			chart.highlightValue(it.toFloat(),y,0)
		}
		mBinding.chartLabelRecycler.layoutManager = layoutManager
		mBinding.chartLabelRecycler.adapter = adapter
	}
	
	private fun setupPieChart() {
		chart.setUsePercentValues(true)
		chart.description.isEnabled = false
		chart.setExtraOffsets(5f,5f,5f,5f)
		
		chart.dragDecelerationFrictionCoef = 0.95f
		
		
		chart.isDrawHoleEnabled = true
		chart.setHoleColor(Color.WHITE)
		
		chart.setTransparentCircleColor(Color.WHITE)
		chart.setTransparentCircleAlpha(110)
		
		chart.holeRadius = 45f
		chart.transparentCircleRadius = 48f
		
		chart.setDrawCenterText(true)
		
		chart.rotationAngle = 0f
		// enable rotation of the chart by touch
		// enable rotation of the chart by touch
		chart.isRotationEnabled = true
		chart.isHighlightPerTapEnabled = true
		
		// chart.setUnit(" €");
		// chart.setDrawUnitsInChart(true);
		
		// add a selection listener
		
		// chart.setUnit(" €");
		// chart.setDrawUnitsInChart(true);
		
		// add a selection listener
//		chart.setOnChartValueSelectedListener(this)
		setData()
		
		chart.animateY(1400,Easing.EaseInOutQuad)
		// chart.spin(2000, 0, 360);
		
		chart.legend.isEnabled = false
		
		// entry label styling
		chart.setEntryLabelColor(Color.WHITE)
		chart.setEntryLabelTextSize(12f)
	}
	
	private fun setData(range: Float = 50f) {
		val entries = ArrayList<PieEntry>()
		
		// NOTE: The order of the entries when being added to the entries array determines their position around the center of
		// the chart.
		if (pieChartDataList.isNotEmpty()) {
			for (i in pieChartDataList.indices) {
				entries.add(
					PieEntry(
						pieChartDataList[i].percent,
						""
					)
				)
			}
		} else {
			val parties = chartLabels.map { it.labelName }.toTypedArray()
			for (i in parties.indices) {
				entries.add(
					PieEntry(
						(Math.random() * range + range / 5).toFloat(),
						""
					)
				)
			}
		}
		
		val dataSet = PieDataSet(entries,"")
		dataSet.setDrawIcons(false)
		dataSet.sliceSpace = 3f
		dataSet.iconsOffset = MPPointF(0f,40f)
		dataSet.selectionShift = 5f
		
		// add a lot of colors
		val colors = ArrayList<Int>()
		
		val labelColors = chartLabels.map { it.labelColor }.toTypedArray()
		for (colorString in labelColors) {
			colors.add(Color.parseColor(colorString))
		}
		dataSet.colors = colors
		//dataSet.setSelectionShift(0f);
		val data = PieData(dataSet)
		data.setValueFormatter(PercentFormatter())
		data.setValueTextSize(11f)
		data.setValueTextColor(Color.WHITE)
		chart.data = data
		
		// undo all highlights
		chart.highlightValues(null)
		chart.invalidate()
	}
	
}