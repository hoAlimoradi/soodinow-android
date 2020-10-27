package com.paya.presentation.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.paya.presentation.R

class MarketAdapter : RecyclerView.Adapter<ViewHolder>() {
	override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): MarketViewHolder {
		val inflate = LayoutInflater.from(parent.context)
		val view = inflate.inflate(R.layout.row_market_currency,parent,false)
		return MarketViewHolder(view)
	}
	
	override fun onBindViewHolder(holder: ViewHolder,position: Int) {
	
	}
	
	override fun getItemCount(): Int {
		return 3
	}
	
	class MarketViewHolder(itemView: View) : ViewHolder(itemView) {
	
	}
}