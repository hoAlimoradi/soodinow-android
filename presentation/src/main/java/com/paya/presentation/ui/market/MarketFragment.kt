package com.paya.presentation.ui.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paya.domain.models.repo.CurrencyPriceRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentMarketBinding
import com.paya.presentation.ui.market.adapter.CurrencyAdapter
import com.paya.presentation.ui.market.adapter.DayAdapter
import com.paya.presentation.ui.market.adapter.StockAdapter
import com.paya.presentation.utils.BaseAdapter
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.MarketViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MarketFragment : BaseFragment<MarketViewModel>() {
	
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
		observe(mViewModel.currencyPrice, ::onPricesReady)

	}
	
	private fun onPricesReady(resource: Resource<List<CurrencyPriceRepoModel>>){

	}
	

	

	override val baseViewModel: BaseViewModel
		get() = mViewModel
	
}