package com.paya.presentation.ui.dividend.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentDividendSummeryBinding
import com.paya.presentation.ui.dividend.adapter.SummeryExchangeAdapter
import com.paya.presentation.ui.dividend.adapter.SummeryPriceAdapter


class DividendSummeryFragment : Fragment() {
	private lateinit var mBinding: FragmentDividendSummeryBinding
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding =
			DataBindingUtil.inflate(inflater,R.layout.fragment_dividend_summery,container,false)
		return mBinding.root
		
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		setupPriceAdapter()
		setupExchangeAdapter()
	}
	
	private fun setupPriceAdapter() {
		val manager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
		mBinding.priceRecyclerView.layoutManager = manager
		mBinding.priceRecyclerView.adapter = SummeryPriceAdapter()
		mBinding.priceRecyclerView.isNestedScrollingEnabled = false
	}
	override fun onDestroy() {
		super.onDestroy()
		mBinding.unbind()
	}
	private fun setupExchangeAdapter() {
		val manager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
		mBinding.exchangeRecyclerView.layoutManager = manager
		mBinding.exchangeRecyclerView.adapter = SummeryExchangeAdapter()
		mBinding.exchangeRecyclerView.isNestedScrollingEnabled = false
	}
}