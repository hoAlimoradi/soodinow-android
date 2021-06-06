package com.paya.presentation.ui.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.paya.domain.models.repo.CurrencyPriceRepoModel
import com.paya.domain.tools.Resource
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentMarketBinding
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.MarketViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MarketFragment : BaseFragment<MarketViewModel>() {

	private var mBinding: FragmentMarketBinding? = null
	private val mViewModel: MarketViewModel by viewModels()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = FragmentMarketBinding.inflate(
			inflater,
			container,
			false
		)
		return mBinding?.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		observe(mViewModel.currencyPrice, ::onPricesReady)

	}
	
	private fun onPricesReady(resource: Resource<List<CurrencyPriceRepoModel>>){

	}


	override fun onDestroy() {
		mBinding = null
		super.onDestroy()
	}

	override val baseViewModel: BaseViewModel
		get() = mViewModel
	
}