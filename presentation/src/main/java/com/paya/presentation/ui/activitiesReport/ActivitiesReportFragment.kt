package com.paya.presentation.ui.activitiesReport

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentActivitiesReportBinding
import com.paya.presentation.ui.activitiesReport.fragments.LoginExitReportFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivitiesReportFragment : Fragment() {
	
	private lateinit var binding: FragmentActivitiesReportBinding
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		binding = DataBindingUtil.inflate(
			inflater,
			R.layout.fragment_activities_report,
			container,
			false
		)

		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupPagerAdapter()
		binding.toolbar.backButton.setOnClickListener {
			findNavController().popBackStack()
		}
	}

	private fun setupPagerAdapter() {
		binding.pager.adapter = PagerAdapter(this)
		TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
			tab.text =
				if (position == 0) getString(R.string.activity_report_login_exit) else getString(R.string.activity_report_financial)
		}.attach()
		binding.pager.post {
			binding.pager.setCurrentItem(1, true)
		}
	}

	private inner class PagerAdapter(f: Fragment) : FragmentStateAdapter(f) {
		override fun getItemCount(): Int = 2

		override fun createFragment(position: Int): Fragment =
			if (position == 0) LoginExitReportFragment() else FinancialReportFragment()
	}

}