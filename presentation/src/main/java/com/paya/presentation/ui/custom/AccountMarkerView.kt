package com.paya.presentation.ui.custom

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.paya.presentation.R

class AccountMarkerView(context: Context) : MarkerView(context,R.layout.account_marker_view) {
	
	private val valueTextView: TextView = findViewById(R.id.salesValue)
	
	
	override fun refreshContent(e: Entry,highlight: Highlight) {
		valueTextView.text = e.y.toString()
		super.refreshContent(e,highlight)
	}
}