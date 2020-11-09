package com.paya.presentation.ui.investment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentAppropriateInvestmentBinding
import kotlinx.android.synthetic.main.fragment_appropriate_investment.*
import java.util.*

class AppropriateInvestmentFragment : Fragment() {
	
	private val parties = arrayOf(
		"بذز","کاریز","افق ملت ","آگاه"
	)
	
	private lateinit var mBinding: FragmentAppropriateInvestmentBinding
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_appropriate_investment,container,false)
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		setupPieChart()
		simulation_btn.setOnClickListener {
			val pop = InvestmentScoreDialog()
			val fm = requireActivity().supportFragmentManager
			pop.show(fm, "name")
		}
		
	}
	
	private fun setupPieChart() {
		chart.setUsePercentValues(true)
		chart.getDescription().setEnabled(false)
		chart.setExtraOffsets(5f,10f,5f,5f)
		
		chart.setDragDecelerationFrictionCoef(0.95f)
		
		
		chart.setDrawHoleEnabled(true)
		chart.setHoleColor(Color.WHITE)
		
		chart.setTransparentCircleColor(Color.WHITE)
		chart.setTransparentCircleAlpha(110)
		
		chart.setHoleRadius(58f)
		chart.setTransparentCircleRadius(61f)
		
		chart.setDrawCenterText(true)
		
		chart.setRotationAngle(0f)
		// enable rotation of the chart by touch
		// enable rotation of the chart by touch
		chart.setRotationEnabled(true)
		chart.setHighlightPerTapEnabled(true)
		
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
		
		// chart.spin(2000, 0, 360);
		val l: Legend = chart.getLegend()
		l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
		l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
		l.orientation = Legend.LegendOrientation.HORIZONTAL
		l.setDrawInside(false)
		l.xEntrySpace = 7f
		l.yEntrySpace = 0f
		l.yOffset = 0f
		
		// entry label styling
		
		// entry label styling
		chart.setEntryLabelColor(Color.WHITE)
		chart.setEntryLabelTextSize(12f)
	}
	
	private fun setData(range: Float = 50f) {
		val entries = ArrayList<PieEntry>()
		
		// NOTE: The order of the entries when being added to the entries array determines their position around the center of
		// the chart.
		for (i in parties.indices) {
			entries.add(
				PieEntry(
					(Math.random() * range + range / 5).toFloat(),
					parties[i % parties.size]
				)
			)
		}
		val dataSet = PieDataSet(entries,"")
		dataSet.setDrawIcons(false)
		dataSet.sliceSpace = 3f
		dataSet.iconsOffset = MPPointF(0f,40f)
		dataSet.selectionShift = 5f
		
		// add a lot of colors
		val colors = ArrayList<Int>()
		colors.add(Color.parseColor("#008C23"))
		colors.add(Color.parseColor("#A9CFA6"))
		colors.add(Color.parseColor("#035058"))
		colors.add(Color.parseColor("#62B366"))
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