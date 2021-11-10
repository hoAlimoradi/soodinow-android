package com.paya.presentation.ui.riskAssessment.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.paya.presentation.ui.riskAssessment.RiskAssessmentQuestionFragment

class RiskAssessmentQuestionsFragmentViewPagerAdapter(fm: FragmentManager,
                                                      var riskAssessmentQuestionFragments: MutableList<RiskAssessmentQuestionFragment>)
    : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return riskAssessmentQuestionFragments[position]
    }

    override fun getCount(): Int {
        //notifyDataSetChanged()
        return riskAssessmentQuestionFragments.size
    }

    fun clear() {
        riskAssessmentQuestionFragments.clear()
    }

}


