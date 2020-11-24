package com.paya.presentation.ui.custom

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.paya.presentation.R

class MyMarkerViewSmall(context: Context) : MarkerView(context,R.layout.popup_linear_chart_small) {
	
	private val priceTextView: TextView = findViewById(R.id.price)
	private val percentTextView: TextView = findViewById(R.id.percent)
	
	override fun refreshContent(e: Entry,highlight: Highlight) {
		priceTextView.text = e.y.toString()
		percentTextView.text = e.y.toString()
		
		super.refreshContent(e,highlight)
	}
}