package com.paya.presentation.ui.investment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentDialogInvesmentScoreBinding
import com.paya.presentation.utils.BindingAdapters
import com.paya.presentation.utils.Utils
import com.paya.presentation.utils.shared.Point


class InvestmentScoreDialog : DialogFragment() {
	
	private lateinit var mBinding: FragmentDialogInvesmentScoreBinding
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
	}
	
	override fun onResume() {
		super.onResume()
		dialog?.window?.let { Utils.setSizeDialog(requireActivity(),it) }
		
	}
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = DataBindingUtil.inflate(
			inflater,
			R.layout.fragment_dialog_invesment_score,
			container,
			false
		)
		
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		mBinding.toolbar.closeBtn.setOnClickListener {
			dismissAllowingStateLoss()
		}
		val points = mutableListOf<Point>()
		for (i in 0 until 10) {
			val value = (Math.random() * 100).toFloat()
			points.add(Point(i.toFloat(),value))
		}
		BindingAdapters.setLineChartData(mBinding.chart,points)
	}
	
}