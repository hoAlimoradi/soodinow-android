package com.paya.presentation.ui.activitiesReport.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paya.presentation.R
import kotlinx.android.synthetic.main.item_login_exit_report.view.*

class LoginExitReportAdapter(): RecyclerView.Adapter<LoginExitReportAdapter.ActivityReportViewHolder>() {
	
	class ActivityReportViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

	}
	
	override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ActivityReportViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val view = inflater.inflate(R.layout.item_login_exit_report,parent,false)
		return ActivityReportViewHolder(
			view
		)
	}
	
	override fun onBindViewHolder(holder: ActivityReportViewHolder, position: Int) {
		holder.itemView.iconImageView.setImageResource(if (position%2==0) R.drawable.ic_arrow_login else R.drawable.ic_arrow_exit)
	}
	
	override fun getItemCount(): Int {
		return 2
	}
	
}