package com.paya.presentation.ui.dividend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentDividendDetailBinding


class DividendDetailFragment : Fragment() {
	
	private lateinit var mBinding: FragmentDividendDetailBinding
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		
		// Inflate the layout for this fragment
		mBinding = DataBindingUtil.inflate(
			inflater,
			R.layout.fragment_dividend_detail,
			container,
			false
		)
		
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		setupTabLayout()
	}
	
	private fun setupTabLayout() {
		val informationTab: TabLayout.Tab = mBinding.tabLayout.newTab()
		informationTab.text = getString(R.string.information)
		informationTab.select()
		val summaryTab: TabLayout.Tab = mBinding.tabLayout.newTab()
		summaryTab.text = getString(R.string.summary)
		val kadalTab: TabLayout.Tab = mBinding.tabLayout.newTab()
		kadalTab.text = getString(R.string.kadal)
		mBinding.tabLayout.addTab(kadalTab)
		mBinding.tabLayout.addTab(summaryTab)
		mBinding.tabLayout.addTab(informationTab)
		mBinding.tabLayout.addOnTabSelectedListener(object :
			TabLayout.OnTabSelectedListener {
			override fun onTabSelected(tab: TabLayout.Tab) {
			
			}
			
			override fun onTabUnselected(tab: TabLayout.Tab) {
			
			}
			
			override fun onTabReselected(tab: TabLayout.Tab) {
			
			}
			
		})
	}
	
}