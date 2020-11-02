package com.paya.presentation.ui.market.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.LineChart
import com.paya.presentation.R
import com.paya.presentation.utils.BindingAdapters
import com.paya.presentation.utils.shared.Point

class CurrencyAdapter : RecyclerView.Adapter<CurrencyAdapter.StockViewHolder>(){
	override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): StockViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val view = inflater.inflate(R.layout.item_currency_detail,parent,false)
		return StockViewHolder(view)
	}
	
	override fun onBindViewHolder(holder: StockViewHolder,position: Int) {
	}
	
	override fun getItemCount(): Int {
		return 4
	}
	
	class StockViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
		
	
	}
	
}