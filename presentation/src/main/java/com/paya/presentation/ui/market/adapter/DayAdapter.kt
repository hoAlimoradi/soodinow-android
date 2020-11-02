package com.paya.presentation.ui.market.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.paya.presentation.R

class DayAdapter : RecyclerView.Adapter<DayAdapter.DayViewHolder>() {
	
	private var selectedItemPosition = 0
	private var selectedHolder: DayViewHolder? = null
	
	override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): DayViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val view = inflater.inflate(R.layout.item_day,parent,false)
		return DayViewHolder(view)
	}
	
	override fun onBindViewHolder(holder: DayViewHolder,position: Int) {
		if (position == selectedItemPosition) {
			selectedHolder = holder
			selectItem(holder)
		} else {
			deselectItem(holder)
		}
		
		holder.dayTxt.setOnClickListener {
			if (position == selectedItemPosition)
				return@setOnClickListener
			selectedHolder?.let { h -> deselectItem(h) }
			selectedItemPosition = position
			selectedHolder = holder
			selectItem(holder)
		}
		
	}
	
	private fun deselectItem(holder: DayViewHolder) {
		holder.dayTxt.setBackgroundResource(R.drawable.round_corner_layout_white)
		holder.dayTxt.setTextColor(
			ContextCompat.getColor(
				holder.dayTxt.context,R.color.black
			)
		)
	}
	
	private fun selectItem(holder: DayViewHolder) {
		holder.dayTxt.setBackgroundResource(R.drawable.round_corner_layout_green)
		holder.dayTxt.setTextColor(
			ContextCompat.getColor(
				holder.dayTxt.context,R.color.white
			)
		)
	}
	
	override fun getItemCount(): Int {
		return 10
	}
	
	class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		
		val dayTxt: TextView = itemView.findViewById(R.id.txt_day)
		
	}
	
}