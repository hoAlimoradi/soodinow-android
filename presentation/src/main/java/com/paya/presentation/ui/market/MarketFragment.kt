package com.paya.presentation.ui.market

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentMarketBinding

class MarketFragment : Fragment() {
	
	private lateinit var mBinding: FragmentMarketBinding
	
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
		return mBinding.root
	}
	
}