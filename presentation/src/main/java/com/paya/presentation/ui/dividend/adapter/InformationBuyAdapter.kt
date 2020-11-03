package com.paya.presentation.ui.dividend.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paya.presentation.R

class InformationBuyAdapter : RecyclerView.Adapter<InformationBuyAdapter.BuyViewHolder>() {
	
	
	override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): BuyViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val view = inflater.inflate(R.layout.row_dividend_information_buy,parent,false)
		return BuyViewHolder(view)
	}
	
	override fun onBindViewHolder(holder: BuyViewHolder,position: Int) {
	
	}
	
	override fun getItemCount(): Int = 4
	
	
	
	class BuyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
	
	}
}