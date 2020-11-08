package com.paya.presentation.ui.question

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.paya.domain.models.repo.QuestionRepoModel
import com.paya.presentation.R

class AnswerAdapter(
	private val items: List<QuestionRepoModel>,
	private val onItemSelected: (Int) -> Unit
): RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder>() {
	
	private var selectedPosition: Int? = null
	private var selectedViewHolder: AnswerViewHolder? = null
	
	class AnswerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
		val titleTxt: TextView = itemView.findViewById(R.id.option_title_txt)
		val selectView: View = itemView.findViewById(R.id.option_select)
	}
	
	override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): AnswerViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val view = inflater.inflate(R.layout.item_option,parent,false)
		return AnswerViewHolder(view)
	}
	
	override fun onBindViewHolder(holder: AnswerViewHolder,position: Int) {
		holder.titleTxt.text = items[position].title
		if (position == selectedPosition){
			selectedViewHolder = holder
			holder.selectView.setBackgroundResource(R.drawable.circle_green)
		}else{
			holder.selectView.setBackgroundResource(R.drawable.circle_white)
		}
		
		holder.itemView.setOnClickListener {
			onItemSelected(position)
			selectedPosition = position
			selectedViewHolder?.selectView?.setBackgroundResource(R.drawable.circle_white)
			holder.selectView.setBackgroundResource(R.drawable.circle_green)
			selectedViewHolder = holder
		}
		
	}
	
	override fun getItemCount() = items.size
	
	fun setSelectedAnswer(position: Int?){
		selectedPosition = position
	}
	
}