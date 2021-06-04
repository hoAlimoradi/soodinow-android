package com.paya.presentation.ui.dividend.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentDividendInformationBinding
import com.paya.presentation.ui.dividend.adapter.InformationBuyAdapter
import com.paya.presentation.ui.dividend.adapter.InformationSalesAdapter
import com.paya.presentation.utils.BindingAdapters


class DividendInformationFragment : Fragment() {
	
	private lateinit var mBinding: FragmentDividendInformationBinding
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding =
			DataBindingUtil.inflate(inflater,R.layout.fragment_dividend_information,container,false)
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		setupSalesAdapter()
		setupBuyAdapter()
		BindingAdapters.setLineChartWithoutData(mBinding.chartSalesAndBuy,100f)
		
	}
	
	private fun setupSalesAdapter() {
		val manager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
		mBinding.salesRecyclerView.layoutManager = manager
		mBinding.salesRecyclerView.adapter = InformationSalesAdapter()
		mBinding.salesRecyclerView.isNestedScrollingEnabled = false
	}
	
	private fun setupBuyAdapter() {
		val manager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
		mBinding.buyRecyclerView.layoutManager = manager
		mBinding.buyRecyclerView.adapter = InformationBuyAdapter()
		mBinding.buyRecyclerView.isNestedScrollingEnabled = false
	}
	override fun onDestroy() {
		super.onDestroy()
		mBinding.unbind()
	}
}