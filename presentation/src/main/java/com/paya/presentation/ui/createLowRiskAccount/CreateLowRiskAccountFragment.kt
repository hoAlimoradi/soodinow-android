package com.paya.presentation.ui.createLowRiskAccount

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.paya.domain.models.repo.IsInRiskListRepoModel
import com.paya.domain.models.repo.PercentRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentCreateLowRiskAccountBinding
import com.paya.presentation.ui.investment.AppropriateInvestmentFragment
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.CreateLowRiskAccountViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateLowRiskAccountFragment : Fragment() {
	
	private val mViewModel: CreateLowRiskAccountViewModel by viewModels()
	private lateinit var mBinding: FragmentCreateLowRiskAccountBinding
	private lateinit var appropriateInvestmentFragment: AppropriateInvestmentFragment
	
	private var percents: PercentRepoModel? = null
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = DataBindingUtil.inflate(
			inflater,
			R.layout.fragment_create_low_risk_account,
			container,
			false
		)
		
		mBinding.lifecycleOwner = this
		mBinding.viewModel = mViewModel
		
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		getLowRiskStocks()
		appropriateInvestmentFragment = childFragmentManager.findFragmentById(R.id.appropriate_investment_fragment) as AppropriateInvestmentFragment
		observe(mViewModel.lowRiskResource, ::onReady)
		
		setupInputPrice()
		
		mBinding.myRadioGroup.setOnCheckedChangeListener { group, checkedId ->
			getLowRiskStocks()
		}
		
		mBinding.submitBtn.setOnClickListener {
			findNavController().navigate(
				CreateLowRiskAccountFragmentDirections.navigationToCalculateProfitCapital(
					percents,
					mBinding.inputPrice.text.toString().filter { it.isDigit() }.toLong()
				)
			)
		}
		
	}
	
	private fun setupInputPrice() {
		val watcher = object : TextWatcher {
			private var searchFor = ""
			
			override fun onTextChanged(s: CharSequence?,start: Int,before: Int,count: Int) {
				val searchText = s.toString().trim()
				if (searchText == searchFor)
					return
				
				searchFor = searchText
				
				lifecycleScope.launch {
					delay(500)  //debounce timeOut
					if (searchText != searchFor)
						return@launch
					
					getLowRiskStocks()
				}
			}
			
			override fun afterTextChanged(s: Editable?) = Unit
			override fun beforeTextChanged(s: CharSequence?,start: Int,count: Int,after: Int) = Unit
		}
		
		mBinding.inputPrice.addTextChangedListener(watcher)
	}
	
	private fun getLowRiskStocks() {
		val price = (mBinding.inputPrice.text.toString().filter {
			it.isDigit()
		}).toLongOrNull() ?: return
		
		val checkedId = mBinding.myRadioGroup.checkedRadioButtonId
		val riskState = if (checkedId == R.id.high_risk) "risk" else "no_risk"
		
		mViewModel.getLowRiskStocks(riskState, price)
	}
	
	private fun onReady(resource: Resource<IsInRiskListRepoModel>){
		when(resource.status){
			Status.SUCCESS -> {
				val response = resource.data?.percent ?: return
				percents = response
				mBinding.lowProfit = response.minimum.percent * 100
				mBinding.lastYearProfit = response.year.percent * 100
				mBinding.highProfit = response.perfect.percent * 100
				
				val basket = resource.data?.basket ?: return
				appropriateInvestmentFragment.basket.addAll(basket)
				appropriateInvestmentFragment.setup()
			}
			Status.ERROR -> Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
			else -> return
		}
	}
	
}