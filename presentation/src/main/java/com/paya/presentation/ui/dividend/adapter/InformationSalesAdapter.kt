package com.paya.presentation.ui.dividend.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paya.presentation.R

class InformationSalesAdapter : RecyclerView.Adapter<InformationSalesAdapter.SalesViewHolder>() {
	
	
	override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): SalesViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val view = inflater.inflate(R.layout.row_dividend_information_sales,parent,false)
		return SalesViewHolder(view)
	}
	
	override fun onBindViewHolder(holder: SalesViewHolder,position: Int) {
	
	}
	
	override fun getItemCount(): Int = 4
	
	
	
	class SalesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
	
	}
}