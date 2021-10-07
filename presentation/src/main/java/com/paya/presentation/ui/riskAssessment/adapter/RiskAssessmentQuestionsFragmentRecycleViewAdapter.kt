package com.paya.presentation.ui.riskAssessment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paya.domain.models.remote.RiskAssessmentQuestionRemoteModel
import com.paya.presentation.R
import kotlinx.android.synthetic.main.row_risk_assessment_question.view.*

class RiskAssessmentQuestionFragmentRecycleViewAdapter(val list: List<RiskAssessmentQuestionRemoteModel>) :
    RecyclerView.Adapter<RiskAssessmentQuestionFragmentRecycleViewAdapter.RiskAssessmentQuestionsFragmentRecycleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RiskAssessmentQuestionsFragmentRecycleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row_risk_assessment_question, parent, false)
        return RiskAssessmentQuestionsFragmentRecycleViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RiskAssessmentQuestionsFragmentRecycleViewHolder, position: Int) {
        val item = list[position]
        with(holder.itemView) {
            riskAssessmentQuestionTitle.text = item.title
        }
    }

    class RiskAssessmentQuestionsFragmentRecycleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}