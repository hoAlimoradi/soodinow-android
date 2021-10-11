package com.paya.presentation.ui.createLowRiskAccount.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.paya.presentation.ui.createLowRiskAccount.FarabiWalletFragment
import com.paya.presentation.ui.createLowRiskAccount.SoodinowWalletFragment

class CreateLowRiskAccountFragmentAdapter(private val myContext: Context, fm: FragmentManager, var totalTabs: Int) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {

            1 -> {
                SoodinowWalletFragment()
            }

            0 -> {
                FarabiWalletFragment()
            }

            else -> Fragment()
        }

    }

    override fun getCount(): Int {
        return totalTabs
    }
}