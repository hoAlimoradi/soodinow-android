package com.paya.presentation.ui.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentMarketBinding
import com.paya.presentation.ui.market.adapter.CurrencyAdapter
import com.paya.presentation.ui.market.adapter.DayAdapter
import com.paya.presentation.ui.market.adapter.StockAdapter
import com.paya.presentation.utils.BaseAdapter
import com.paya.presentation.viewmodel.MarketViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MarketFragment : Fragment() {
	
	private lateinit var mBinding: FragmentMarketBinding
	private val mViewModel: MarketViewModel by viewModels()
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = DataBindingUtil.inflate(
			inflater,
			R.layout.fragment_market,
			container,
			false
		)
		
		mBinding.viewModel = mViewModel
		mBinding.lifecycleOwner = this
		
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		mViewModel.getPoints()
		setupStockRecyclerView()
		setupCurrencyRecyclerView()
		setupTagsRecyclerView()
	}
	
	private fun setupStockRecyclerView() {
		val layoutManager = LinearLayoutManager(context)
		val adapter = StockAdapter()
		adapter.addFakeItem("")
		adapter.addOnClickListenerItem(object : BaseAdapter.OnClickListenerItem<String> {
			override fun onClickListenerItem(adapter: BaseAdapter<*,String>,item: String,position: Int) {
				findNavController().navigate(
					MarketFragmentDirections.navigateToDividendFragment()
				)
			}
		})
		mBinding.stockRecyclerView.layoutManager = layoutManager
		mBinding.stockRecyclerView.adapter = adapter
	}
	
	private fun setupCurrencyRecyclerView() {
		val layoutManager = LinearLayoutManager(context)
		val adapter = CurrencyAdapter()
		mBinding.currencyRecyclerView.layoutManager = layoutManager
		mBinding.currencyRecyclerView.adapter = adapter
	}
	
	private fun setupTagsRecyclerView() {
		val layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,true)
		val adapter = DayAdapter()
		mBinding.tagsRecyclerView.layoutManager = layoutManager
		mBinding.tagsRecyclerView.adapter = adapter
	}
	
}