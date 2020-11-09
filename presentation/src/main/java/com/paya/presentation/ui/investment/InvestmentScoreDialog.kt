package com.paya.presentation.ui.investment

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentDialogInvesmentScoreBinding
import com.paya.presentation.ui.createPersonalAccount.adapter.DayAdapter
import com.paya.presentation.utils.BindingAdapters
import com.paya.presentation.utils.shared.Point


class InvestmentScoreDialog : DialogFragment() {
	
	private lateinit var mBinding: FragmentDialogInvesmentScoreBinding
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
	}
	
	override fun onResume() {
		super.onResume()
		val window: Window? = dialog!!.window
		window!!.setLayout(resources.getDimension(R.dimen.dialog_w).toInt(),resources.getDimension(R.dimen.dialog_h).toInt())
		window!!.setGravity(Gravity.CENTER)
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
		setupTagsRecyclerView()
		val points = mutableListOf<Point>()
		for (i in 0 until 10) {
			val value = (Math.random() * 100).toFloat()
			points.add(Point(i.toFloat(),value))
		}
		BindingAdapters.setLineChartData(mBinding.chart,points)
	}
	
	private fun setupTagsRecyclerView() {
		val layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,true)
		val adapter = DayAdapter()
		mBinding.dayRecycler.layoutManager = layoutManager
		mBinding.dayRecycler.adapter = adapter
	}
}