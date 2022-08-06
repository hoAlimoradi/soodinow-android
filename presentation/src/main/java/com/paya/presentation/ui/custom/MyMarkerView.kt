package com.paya.presentation.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Utils
import com.paya.presentation.R
import com.paya.presentation.utils.shared.EntryPoint

@SuppressLint("ViewConstructor")
class MyMarkerView(context: Context,private val parentView: View) :
	MarkerView(context,R.layout.popup_linear_chart) {
	
	private val priceTextView: TextView = findViewById(R.id.price)
	private val percentTextView: TextView = findViewById(R.id.percent)
	private var posY: Int? = null
	private var posX: Int? = null
	
	override fun refreshContent(e: Entry,highlight: Highlight) {
		if (e is EntryPoint) {
			percentTextView.text = (if (e.percent == 0.0f) Utils.formatNumber(
				e.y,
				0,
				true
			) else e.percent.toString()) + " %"
			
			priceTextView.text =  (if (e.price == 0L) Utils.formatNumber(
				e.y,
				0,
				true
			) else e.price.toString())
		} else {
			priceTextView.text = e.y.toString()
			percentTextView.text = e.y.toString()
		}
		
		super.refreshContent(e,highlight)
	}
	
	override fun getOffset(): MPPointF? {
		val lineChartWidth = parentView.width
		val lineChartHeight = parentView.height
		var x = -(width / 2)
		var y = -height
		
		if (posX!! + width > lineChartWidth) {
			val over = if ((lineChartWidth - posX!!) == 0) 1 else (lineChartWidth - posX!!)
			x = -(width - over)
		}
		if (height + posY!! < lineChartHeight) {
			y = 5
		}
		
		return MPPointF(x.toFloat(),y.toFloat())
	}
	
	override fun getOffsetForDrawingAtPoint(posX: Float,posY: Float): MPPointF {
		this.posX = posX.toInt()
		this.posY = posY.toInt()
		return super.getOffsetForDrawingAtPoint(posX,posY)
	}
}