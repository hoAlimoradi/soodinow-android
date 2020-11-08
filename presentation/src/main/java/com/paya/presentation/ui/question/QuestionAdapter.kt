package com.paya.presentation.ui.question

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paya.domain.models.repo.QuestionsRepoModel
import com.paya.presentation.R

class QuestionAdapter(
	private val items: List<QuestionsRepoModel>
): RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {
	
	private val answerAdapters = mutableListOf<AnswerAdapter?>()
	
	init {
		for(i in items.indices){
			answerAdapters.add(null)
		}
	}
	
	class QuestionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
		val questionNumberTxt: TextView = itemView.findViewById(R.id.txt_question_number)
		val titleTxt: TextView = itemView.findViewById(R.id.txt_title)
		val optionRecycler: RecyclerView = itemView.findViewById(R.id.option_recycler)
	}
	
	override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): QuestionViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val view = inflater.inflate(R.layout.item_question,parent,false)
		return QuestionViewHolder(view)
	}
	
	override fun onBindViewHolder(holder: QuestionViewHolder,position: Int) {
		holder.questionNumberTxt.text = (position + 1).toString()
		holder.titleTxt.text = items[position].title
		
		val answerAdapter = answerAdapters[position]
		if (answerAdapter == null){
			val manager = LinearLayoutManager(holder.optionRecycler.context)
			val adapter = AnswerAdapter(items[position].answers){
				items[position].selectedAnswer = it
			}
			holder.optionRecycler.layoutManager = manager
			holder.optionRecycler.adapter = adapter
			
			answerAdapters[position] = adapter
		}else{
			val manager = LinearLayoutManager(holder.optionRecycler.context)
			holder.optionRecycler.layoutManager = manager
			answerAdapter.setSelectedAnswer(items[position].selectedAnswer)
			holder.optionRecycler.adapter = answerAdapter
		}
	}
	
	override fun getItemCount() = items.size
	
}