package com.paya.presentation.ui.activitiesReport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentActivitiesReportBinding
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
	
}