package com.paya.presentation.ui.question

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.paya.domain.models.repo.QuestionRepoModel
import com.paya.presentation.R

class AnswerAdapter(
	private val items: List<QuestionRepoModel>,
	private val type: String,
	private val onItemSelected: (Int) -> Unit,
	private val onItemDeselect: (Int) -> Unit,
	private val onValueChanged: (Int, String?) -> Unit
) : RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder>() {
	
	private val selectedPosition = mutableListOf<Int?>()
	private val values = mutableMapOf<Int, String?>()
	private var selectedViewHolder: AnswerViewHolder? = null
	
	class AnswerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val titleTxt: TextView = itemView.findViewById(R.id.option_title_txt)
		val selectView: View = itemView.findViewById(R.id.option_select)
		val input: EditText = itemView.findViewById(R.id.input)
	}
	
	override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): AnswerViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val view = inflater.inflate(R.layout.item_option,parent,false)
		return AnswerViewHolder(view)
	}
	
	override fun onBindViewHolder(holder: AnswerViewHolder,position: Int) {
		holder.titleTxt.text = items[position].title
		if (selectedPosition.contains(position)) {
			selectedViewHolder = holder
			holder.selectView.setBackgroundResource(R.drawable.circle_green)
		} else {
			holder.selectView.setBackgroundResource(R.drawable.circle_white)
		}
		
		if (type == "v") {
			holder.input.visibility = View.VISIBLE
			values[position]?.let { holder.input.setText(it)}
			holder.input.doAfterTextChanged {
				values[position] = it?.toString()
				onValueChanged(position, it?.toString())
			}
		} else {
			holder.input.visibility = View.GONE
		}
		
		if (type == "s") {
			holder.itemView.setOnClickListener {
				selectedPosition.firstOrNull()?.let { it1 -> onItemDeselect(it1) }
				onItemSelected(position)
				selectedPosition.clear()
				selectedPosition.add(position)
				selectedViewHolder?.selectView?.setBackgroundResource(R.drawable.circle_white)
				holder.selectView.setBackgroundResource(R.drawable.circle_green)
				selectedViewHolder = holder
			}
		}else if (type == "v"){
		
		}else{
			holder.itemView.setOnClickListener {
				if (selectedPosition.contains(position)){
					holder.selectView.setBackgroundResource(R.drawable.circle_white)
					onItemDeselect(position)
					selectedPosition.remove(position)
				}else{
					holder.selectView.setBackgroundResource(R.drawable.circle_green)
					onItemSelected(position)
					selectedPosition.add(position)
				}
			}
		}
		
	}
	
	override fun getItemCount() = items.size
	
	fun setSelectedAnswer(position: List<Int>) {
		selectedPosition.clear()
		selectedPosition.addAll(position)
	}
	
	fun setValues(values: Map<Int, String?>){
		this.values.clear()
		this.values.putAll(values)
	}
	
}