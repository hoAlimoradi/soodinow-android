package com.paya.presentation.ui.custom

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Utils
import com.paya.presentation.R

class MyMarkerViewSmall(context: Context) : MarkerView(context,R.layout.popup_linear_chart_small) {
	
	private val percentTextView: TextView = findViewById(R.id.percent)
	
	override fun refreshContent(e: Entry,highlight: Highlight?) {
		if (e is CandleEntry) {
			percentTextView.text = Utils.formatNumber(e.high,0,true)  + " %"
		} else {
			percentTextView.text = Utils.formatNumber(e.y,0,true)  + " %"
		}
		
		super.refreshContent(e,highlight)
	}
	
	override fun getOffset(): MPPointF? {
		return MPPointF((-(width / 2)).toFloat(),(-height).toFloat())
	}
}