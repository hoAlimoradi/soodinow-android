package com.paya.presentation.ui.dividend.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paya.presentation.R

class SummeryPriceAdapter : RecyclerView.Adapter<SummeryPriceAdapter.PriceViewHolder>() {
	
	
	override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): PriceViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val view = inflater.inflate(R.layout.row_dividend_detail_price,parent,false)
		return PriceViewHolder(view)
	}
	
	override fun onBindViewHolder(holder: PriceViewHolder,position: Int) {
	
	}
	
	override fun getItemCount(): Int = 10
	
	
	
	class PriceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
	
	}
}