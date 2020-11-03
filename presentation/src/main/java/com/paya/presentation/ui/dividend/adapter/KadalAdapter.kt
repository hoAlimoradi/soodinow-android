package com.paya.presentation.ui.dividend.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paya.presentation.R

class KadalAdapter : RecyclerView.Adapter<KadalAdapter.KadalViewHolder>() {
	
	
	override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): KadalViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val view = inflater.inflate(R.layout.row_dividend_kadal,parent,false)
		return KadalViewHolder(view)
	}
	
	override fun onBindViewHolder(holder: KadalViewHolder,position: Int) {
	
	}
	
	override fun getItemCount(): Int = 30
	
	
	
	class KadalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
	
	}
}