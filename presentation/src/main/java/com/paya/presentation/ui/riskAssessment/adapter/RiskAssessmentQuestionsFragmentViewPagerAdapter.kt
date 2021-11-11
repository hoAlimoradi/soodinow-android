package com.paya.presentation.ui.riskAssessment.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.paya.presentation.ui.riskAssessment.RiskAssessmentQuestionFragment

class RiskAssessmentQuestionsFragmentViewPagerAdapter(fragmentActivity: FragmentActivity,
                                                      var riskAssessmentQuestionFragments: MutableList<RiskAssessmentQuestionFragment>)
    :FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return riskAssessmentQuestionFragments[position]
    }

    override fun getItemCount(): Int {
        return riskAssessmentQuestionFragments.size
    }

    fun clear() {
        riskAssessmentQuestionFragments.clear()
    }
}





