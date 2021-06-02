package com.paya.presentation.ui.investment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentProfitMethodBinding
import com.paya.presentation.utils.Utils


class ProfitMethodFragment : Fragment() {
	
	private lateinit var mBinding: FragmentProfitMethodBinding

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
			R.layout.fragment_profit_method,
			container,
			false
		)
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		setup()
	}
	
	fun setup() {
		/*percent?.let {
			mBinding.progressIdeal.setValue(it.perfect.percent * 100)
			mBinding.progressLowProfit.setValue(it.minimum.percent * 100)
			mBinding.progressYearsOld.setValue(it.year.percent * 100)
			
			mBinding.txtIdealPrice.text = Utils.separatorAmount(it.perfect.price.toBigDecimal().toBigInteger().toString())
			mBinding.txtLowProfitPrice.text = Utils.separatorAmount(it.minimum.price.toBigDecimal().toBigInteger().toString())
			mBinding.txtYearsOldPrice.text = Utils.separatorAmount(it.year.price.toBigDecimal().toBigInteger().toString())
		}*/
		
	}
	
	
}