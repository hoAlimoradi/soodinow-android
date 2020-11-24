package com.paya.presentation.ui.activitiesReport

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.paya.presentation.R

class ActivityReportAdapter(): RecyclerView.Adapter<ActivityReportAdapter.ActivityReportViewHolder>() {
	
	class ActivityReportViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
		val numberTxt: TextView = itemView.findViewById(R.id.txt_number)
	}
	
	override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ActivityReportViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val view = inflater.inflate(R.layout.item_activity_report,parent,false)
		return ActivityReportViewHolder(view)
	}
	
	override fun onBindViewHolder(holder: ActivityReportViewHolder,position: Int) {
		holder.numberTxt.text = (position + 1).toString()
	}
	
	override fun getItemCount(): Int {
		return 20
	}
	
}