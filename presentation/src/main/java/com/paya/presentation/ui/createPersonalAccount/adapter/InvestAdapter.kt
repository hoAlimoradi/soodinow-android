package com.paya.presentation.ui.createPersonalAccount.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paya.presentation.R

class InvestAdapter : RecyclerView.Adapter<InvestAdapter.InvestViewHolder>() {

	
	override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): InvestViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val view = inflater.inflate(R.layout.row_invest,parent,false)
		return InvestViewHolder(view)
	}
	
	override fun onBindViewHolder(holder: InvestViewHolder,position: Int) {
	
		
	}
	
	override fun getItemCount(): Int {
		return 1
	}
	
	class InvestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
	
		
	}
	
}