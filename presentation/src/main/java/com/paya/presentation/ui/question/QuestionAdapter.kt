package com.paya.presentation.ui.question

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paya.domain.models.remote.UserTestBody
import com.paya.domain.models.repo.QuestionsRepoModel
import com.paya.presentation.R
import java.lang.Exception

class QuestionAdapter(
	private val items: List<QuestionsRepoModel>
) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {
	
	private val answerAdapters = mutableMapOf<Int,AnswerAdapter?>()
	private val test = mutableListOf<UserTestBody>()
	
	class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
		
		val question = items[position]
		
		val answerAdapter = answerAdapters[position]
		if (answerAdapter == null) {
			val manager = LinearLayoutManager(holder.optionRecycler.context)
			val adapter = AnswerAdapter(items[position].answers,items[position].type,
				{
					items[position].selectedAnswer.add(it)
				},
				{
					items[position].selectedAnswer.remove(it)
				},
				{p, value ->
					items[position].values[p] = value
				}
			)
			holder.optionRecycler.layoutManager = manager
			holder.optionRecycler.adapter = adapter
			
			answerAdapters[position] = adapter
		} else {
			val manager = LinearLayoutManager(holder.optionRecycler.context)
			holder.optionRecycler.layoutManager = manager
			answerAdapter.setSelectedAnswer(items[position].selectedAnswer)
			answerAdapter.setValues(items[position].values)
			holder.optionRecycler.adapter = answerAdapter
		}
	}
	
	override fun getItemCount() = items.size
	
	fun getUserTestResult(): List<UserTestBody>{
		val test = mutableListOf<UserTestBody>()
		
		items.forEach {
			if (it.type == "v"){
				for((p, v) in it.values){
					val answerSlug = try {
						it.answers[p].slug
					}catch (e: Exception){
						null
					}
					if (answerSlug != null){
						test.add(UserTestBody(
							it.slug,
							answerSlug,
							v
						))
					}
				}
			}else{
				it.selectedAnswer.forEach {selectedAnswerPosition ->
					val answerSlug = try {
						it.answers[selectedAnswerPosition].slug
					}catch (e: Exception){
						null
					}
					if (answerSlug != null){
						test.add(UserTestBody(
							it.slug,
							answerSlug,
							null
						))
					}
				}
			}
		}
		
		return test
	}
	
}