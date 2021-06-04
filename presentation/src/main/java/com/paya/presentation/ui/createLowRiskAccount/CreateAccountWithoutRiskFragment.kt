package com.paya.presentation.ui.createLowRiskAccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentCreateAccountWithoutRiskBinding
import com.paya.presentation.ui.createPersonalAccount.adapter.DayAdapter
import com.paya.presentation.utils.BindingAdapters
import com.paya.presentation.utils.shared.Point


class CreateAccountWithoutRiskFragment : Fragment() {
	private lateinit var mBinding: FragmentCreateAccountWithoutRiskBinding
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
	}
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = DataBindingUtil.inflate(
			inflater,
			R.layout.fragment_create_account_without_risk,
			container,
			false
		)
		
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		mBinding.createAccountBtn.setOnClickListener {
			/*findNavController().navigate(
				CreateAccountWithoutRiskFragmentDirections.navigationToCreateLowRiskAccount()
			)*/
		}
		setupTagsRecyclerView()
		val points = mutableListOf<Point>()
		for (i in 0 until 10) {
			val value = (Math.random() * 100).toFloat()
			points.add(Point(i.toFloat(),value))
		}
		BindingAdapters.setLineChartData(mBinding.chart,points)
	}
	override fun onDestroy() {
		super.onDestroy()
		mBinding.unbind()
	}
	private fun setupTagsRecyclerView() {
		val layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,true)
		val adapter = DayAdapter()
		mBinding.dayRecycler.layoutManager = layoutManager
		mBinding.dayRecycler.adapter = adapter
	}
}