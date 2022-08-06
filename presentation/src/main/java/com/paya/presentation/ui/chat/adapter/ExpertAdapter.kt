package com.paya.presentation.ui.chat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paya.presentation.R
import kotlinx.android.synthetic.main.row_expert_chat.view.*

class ExpertAdapter : RecyclerView.Adapter<ExpertAdapter.ExpertViewHolder>() {
	
	override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ExpertViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val view = inflater.inflate(R.layout.row_expert_chat,parent,false)
		return ExpertViewHolder(view)
	}
	
	override fun onBindViewHolder(holder: ExpertViewHolder,position: Int) {
		with(holder.itemView) {
			image.setImageResource(R.drawable.ic_chat_more)
		}
	}
	
	override fun getItemCount(): Int {
		return 5
	}
	
	class ExpertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}