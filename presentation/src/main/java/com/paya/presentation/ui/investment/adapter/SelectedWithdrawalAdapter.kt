package com.paya.presentation.ui.investment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.paya.presentation.R
import com.paya.presentation.utils.BaseAdapter

class SelectedWithdrawalAdapter : BaseAdapter<SelectedWithdrawalAdapter.SelectedDayInMonthsViewHolder,String>() {
	
	init {
		addMonth()
	}
	
	class SelectedDayInMonthsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val dayTextView: AppCompatTextView = itemView.findViewById(R.id.day)
	}
	
	override fun onCreateHolder(parent: ViewGroup,viewType: Int): SelectedDayInMonthsViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val view = inflater.inflate(R.layout.seleted_day_in_month,parent,false)
		return SelectedDayInMonthsViewHolder(view)
	}
	
	override fun onBindHolder(holder: SelectedDayInMonthsViewHolder,item: String,position: Int) {
		holder.dayTextView.text = item
	}
	
	private fun addMonth() {
		for (i in 1..4) {
			addLastItem(i.toString())
		}
	}
}