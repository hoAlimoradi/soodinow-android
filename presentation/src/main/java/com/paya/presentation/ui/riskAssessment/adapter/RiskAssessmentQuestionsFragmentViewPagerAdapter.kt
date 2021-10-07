package com.paya.presentation.ui.riskAssessment.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.paya.presentation.ui.createLowRiskAccount.FarabiWalletFragment
import com.paya.presentation.ui.createLowRiskAccount.SoodinowWalletFragment
import com.paya.presentation.ui.riskAssessment.RiskAssessmentQuestionFragment

class RiskAssessmentQuestionsFragmentViewPagerAdapter(private val myContext: Context, fm: FragmentManager, var riskAssessmentQuestionFragments: List<RiskAssessmentQuestionFragment>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return riskAssessmentQuestionFragments[position]
    }

    override fun getCount(): Int {
        return riskAssessmentQuestionFragments.size
    }
}

