package com.paya.presentation.utils

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import at.grabner.circleprogress.CircleProgressView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.paya.presentation.R
import com.paya.presentation.ui.custom.MyMarkerView
import com.paya.presentation.ui.custom.MyMarkerViewSmall
import com.paya.presentation.utils.shared.EntryPoint
import com.paya.presentation.utils.shared.Point

object BindingAdapters {
	
	@JvmStatic
	@BindingAdapter("percentText")
	fun setPercentText(textView: TextView,value: String?) {
		value ?: return
		val percentText = "% $value"
		textView.text = percentText
	}
	
	@JvmStatic
	@BindingAdapter("progressValue")
	fun setProgressValue(circleProgressView: CircleProgressView, value: Float?){
		value ?: return
		circleProgressView.setValue(value)
	}
	
	@JvmStatic
	@BindingAdapter("enableDisable")
	fun setTime(textView: TextView,value: Int?) {
		value ?: return
		if (value != 0)
			textView.setTextColor(ContextCompat.getColor(textView.context,R.color.gray))
		else
			textView.setTextColor(ContextCompat.getColor(textView.context,R.color.green))
	}
	@JvmStatic
	@BindingAdapter("changeBgRoundPurple")
	fun changeBgRoundPurple(view: Button,value: Boolean?) {
		value ?: return
		if (value) {
			view.setBackgroundResource(R.drawable.round_corner_layout_purple)
			view.setTextColor(ContextCompat.getColor(view.context,R.color.white))
		}
		else {
			view.setBackgroundColor(Color.TRANSPARENT)
			view.setTextColor(ContextCompat.getColor(view.context,R.color.gray))
		}
	}
	
	
	@JvmStatic
	@BindingAdapter("visibleGone")
	fun showHide(view: View,show: Boolean) {
		view.visibility = if (show) View.VISIBLE else View.GONE
	}
	
	@JvmStatic
	@BindingAdapter("focusTarget")
	fun setFocusTarget(view: View,viewId: Int?) {
		viewId ?: return
		view.setOnClickListener {
			val target = view.findViewById<EditText>(viewId)
			target.requestFocus()
			val imm: InputMethodManager? =
				target.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
			imm?.showSoftInput(target,InputMethodManager.SHOW_IMPLICIT)
		}
	}
	
	@JvmStatic
	@BindingAdapter("verificationImage")
	fun setVerificationImage(editText: EditText,viewId: Int?) {
		viewId ?: return
		
		val parent = editText.parent as View
		val imageView = parent.findViewById<ImageView>(viewId)
		setTintColor(imageView,editText.context,R.color.gray)
		
		editText.doAfterTextChanged {
			val text = it.toString()
			val colorId = if (text.length != 9) R.color.gray else R.color.green
			setTintColor(imageView,editText.context,colorId)
		}
	}
	
	@JvmStatic
	@BindingAdapter("hintLabel")
	fun setHintLabel(editText: EditText,value: String?) {
		value ?: return
		editText.hint = value
	}
	
	@JvmStatic
	@BindingAdapter("chartData")
	fun setLineChartData(chart: LineChart,data: List<Point>?) {
		data ?: return
		chart.setViewPortOffsets(0f,0f,0f,0f)
		chart.setBackgroundColor(Color.WHITE)
		
		// no description text
		chart.description.isEnabled = false
		
		// enable touch gestures
		chart.setTouchEnabled(true)
		
		// enable scaling and dragging
		chart.isDragEnabled = true
		chart.setScaleEnabled(true)
		
		// if disabled, scaling can be done on x- and y-axis separately
		chart.setPinchZoom(false)
		
		chart.setDrawGridBackground(false)
		chart.maxHighlightDistance = 300f
		
		chart.xAxis.isEnabled = false
		
		chart.axisLeft.isEnabled = false
		
		chart.axisRight.isEnabled = false
		
		chart.marker = MyMarkerView(chart.context)
		
		setData(chart,data)
		
		chart.legend.isEnabled = false
		
		chart.animateXY(1000,1000)
		
		// don't forget to refresh the drawing
		chart.invalidate()
	}
	
	private fun setData(chart: LineChart,points: List<Point>) {
		val set1: LineDataSet
		
		val entries = points.map { Entry(it.x,it.y) }
		
		if (chart.data != null &&
			chart.data.dataSetCount > 0
		) {
			set1 = chart.data.getDataSetByIndex(0) as LineDataSet
			set1.values = entries
			chart.data.notifyDataChanged()
			chart.notifyDataSetChanged()
		} else {
			// create a dataset and give it a type
			set1 = LineDataSet(entries,"DataSet 1")
			set1.mode = LineDataSet.Mode.CUBIC_BEZIER
			set1.cubicIntensity = 0.2f
			set1.setDrawFilled(true)
			set1.setDrawCircles(true)
			set1.lineWidth = 3f
			set1.circleRadius = 1f
			set1.setCircleColor(Color.BLACK)
			set1.highLightColor = Color.RED
			set1.color = ContextCompat.getColor(chart.context,R.color.green)
			set1.fillColor = ContextCompat.getColor(chart.context,R.color.green)
			set1.fillAlpha = 30
			set1.setDrawHorizontalHighlightIndicator(true)
			set1.fillFormatter =
				IFillFormatter { _,_ -> chart.axisLeft.axisMinimum }
			
			// create a data object with the data sets
			val data = LineData(set1)
			data.setValueTextSize(9f)
			data.setDrawValues(false)
			
			// set data
			chart.data = data
		}
	}
	
	@JvmStatic
	@BindingAdapter("chartWithoutData")
	fun setLineChartWithoutData(chart: LineChart,axisMaximum: Float) {
		
		chart.description.isEnabled = false
		chart.setTouchEnabled(false)
		chart.isDragEnabled = false
		chart.setScaleEnabled(true)
		chart.setDrawGridBackground(false)
		chart.setPinchZoom(false)
		chart.setBackgroundColor(Color.WHITE)
		val data = LineData()
		data.setValueTextColor(Color.GREEN)
		chart.data = data
		
		val xl = chart.xAxis
		xl.textColor = ContextCompat.getColor(chart.context,R.color.purple_gray)
		xl.setDrawGridLines(false)
		xl.setAvoidFirstLastClipping(true)
		xl.isEnabled = true
		
		val leftAxis = chart.axisLeft
		leftAxis.textColor = ContextCompat.getColor(chart.context,R.color.purple_gray)
		leftAxis.axisLineColor = ContextCompat.getColor(chart.context,R.color.purple_gray)
		leftAxis.gridColor = ContextCompat.getColor(chart.context,R.color.purple_gray)
		leftAxis.axisMaximum = axisMaximum
		leftAxis.axisMinimum = 0f
		leftAxis.setDrawZeroLine(true)
		leftAxis.setDrawGridLines(true)
		
		val rightAxis = chart.axisRight
		rightAxis.isEnabled = false
	}
	
	
	@JvmStatic
	@BindingAdapter("accountChartData")
	fun setLineAccountChartData(chart: LineChart,data: List<Point>?) {
		data ?: return
		chart.setViewPortOffsets(0f,0f,0f,0f)
		chart.setBackgroundColor(Color.TRANSPARENT)
		
		// no description text
		chart.description.isEnabled = false
		
		// enable touch gestures
		chart.setTouchEnabled(false)
		
		// enable scaling and dragging
		chart.isDragEnabled = true
		chart.setScaleEnabled(true)
		
		// if disabled, scaling can be done on x- and y-axis separately
		chart.setPinchZoom(false)
		
		
		chart.setDrawGridBackground(false)
		chart.maxHighlightDistance = 300f
		
		chart.xAxis.isEnabled = false
		
		chart.axisLeft.isEnabled = false
		
		chart.axisRight.isEnabled = false
		
		chart.marker = MyMarkerViewSmall(chart.context)
		
		
		setAccountData(chart,data)
		
		chart.legend.isEnabled = false
		
		chart.animateXY(1000,1000)
		
		// don't forget to refresh the drawing
		chart.invalidate()
		
		val index = chart.data.dataSets[0].entryCount - 1
		
		val x = chart.data.dataSets[0].getEntryForIndex(index).x
		
		val y = chart.data.dataSets[0].getEntryForIndex(index).y
		
		chart.highlightValue(x,y,0)
		chart.marker.refreshContent(
			chart.data.dataSets[0].getEntryForIndex(index),
			chart.getHighlightByTouchPoint(x,y)
		)
	}
	
	private fun setAccountData(chart: LineChart,points: List<Point>) {
		val set1: LineDataSet
		
		val entries = points.map { EntryPoint(it.x,it.y,it.percent) }
		
		if (chart.data != null &&
			chart.data.dataSetCount > 0
		) {
			set1 = chart.data.getDataSetByIndex(0) as LineDataSet
			set1.values = entries
			chart.data.notifyDataChanged()
			chart.notifyDataSetChanged()
		} else {
			// create a dataset and give it a type
			set1 = LineDataSet(entries,"DataSet 1")
			set1.mode = LineDataSet.Mode.CUBIC_BEZIER
			set1.cubicIntensity = 0.2f
			set1.setDrawFilled(true)
			set1.setDrawCircles(true)
			set1.lineWidth = 2f
			set1.circleRadius = 0f
			set1.setCircleColor(Color.TRANSPARENT)
			set1.color = Color.WHITE
			//set1.fillColor = ContextCompat.getColor(chart.context,R.color.white)
			set1.fillAlpha = 0
			set1.setDrawHorizontalHighlightIndicator(true)
			set1.fillFormatter =
				IFillFormatter { _,_ -> chart.axisLeft.axisMinimum }
			// create a data object with the data sets
			val data = LineData(set1)
			data.setValueTextSize(9f)
			data.setDrawValues(false)
			
			// set data
			chart.data = data
		}
	}
	
	private fun setTintColor(
		imageView: ImageView,
		context: Context,
		colorId: Int
	) {
		imageView.setColorFilter(
			ContextCompat.getColor(context,colorId),
			PorterDuff.Mode.SRC_IN
		)
	}
	
}