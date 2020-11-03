package com.paya.presentation.ui.market.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.LineChart
import com.paya.presentation.R
import com.paya.presentation.utils.BaseAdapter
import com.paya.presentation.utils.BindingAdapters
import com.paya.presentation.utils.shared.Point

class StockAdapter : BaseAdapter<StockAdapter.StockViewHolder,String>() {
	
	override fun onCreateHolder(parent: ViewGroup,viewType: Int): StockViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val view = inflater.inflate(R.layout.item_market_stock,parent,false)
		return StockViewHolder(view)
	}
	
	override fun onBindHolder(holder: StockViewHolder,item: String,position: Int) {
		val points = mutableListOf<Point>()
		for (i in 0 until 10) {
			val value = (Math.random() * 100).toFloat()
			points.add(Point(i.toFloat(),value))
		}
		BindingAdapters.setLineChartData(holder.chart,points)
		
		val manager = LinearLayoutManager(holder.dayRecycler.context,RecyclerView.HORIZONTAL,true)
		val adapter = DayAdapter()
		holder.dayRecycler.layoutManager = manager
		holder.dayRecycler.adapter = adapter
	}
	
	
	class StockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val chart: LineChart = itemView.findViewById(R.id.chart)
		val dayRecycler: RecyclerView = itemView.findViewById(R.id.dayRecycler)
		
	}
	
}