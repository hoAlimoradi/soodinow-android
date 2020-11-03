package com.paya.presentation.ui.dividend.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paya.presentation.R

class SummeryExchangeAdapter : RecyclerView.Adapter<SummeryExchangeAdapter.ExchangeViewHolder>() {
	
	
	override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ExchangeViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val view = inflater.inflate(R.layout.row_dividend_detail_exchange,parent,false)
		return ExchangeViewHolder(view)
	}
	
	override fun onBindViewHolder(holder: ExchangeViewHolder,position: Int) {
	
	}
	
	override fun getItemCount(): Int = 10
	
	
	
	class ExchangeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
	
	}
}