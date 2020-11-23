package com.paya.presentation.ui.createPersonalAccount.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.paya.presentation.R

class InvestAdapter(
	private val onItemClick: (position: Int) -> Unit
) : RecyclerView.Adapter<InvestAdapter.InvestViewHolder>() {
	
	
	override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): InvestViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val view = inflater.inflate(R.layout.row_invest,parent,false)
		return InvestViewHolder(view)
	}
	
	override fun onBindViewHolder(holder: InvestViewHolder,position: Int) {
		
		holder.button.setOnClickListener {
			onItemClick(position)
		}
		
	}
	
	override fun getItemCount(): Int {
		return 1
	}
	
	class InvestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val button: AppCompatButton = itemView.findViewById(R.id.addInvestBtn)
		
		
	}
	
}