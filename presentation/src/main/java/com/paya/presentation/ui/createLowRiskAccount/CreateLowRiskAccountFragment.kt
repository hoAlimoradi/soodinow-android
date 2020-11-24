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
import com.paya.presentation.utils.NumberTextWatcher
import com.paya.presentation.utils.Utils
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.CreateLowRiskAccountViewModel
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_calculate_profit_capital.*
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
			val price = mBinding.inputPrice.text.toString().filter { it.isDigit() }.toLong()
			if (price <= 5000000){
				Toast.makeText(
					requireContext(), getString(R.string.price_error), Toast.LENGTH_SHORT
				).show()
				return@setOnClickListener
			}
			findNavController().navigate(
				CreateLowRiskAccountFragmentDirections.navigationToCalculateProfitCapital(
					percents,
					mBinding.inputPrice.text.toString().filter { it.isDigit() }.toLong(),
					(if (mBinding.highRisk.isChecked) "risk" else "no_risk")
				)
			)
		}
		
	}
	
	private fun setupSeekBar() {
		seekBarPrice.onSeekChangeListener = object : OnSeekChangeListener {
			override fun onSeeking(seekParams: SeekParams?) {
				if (seekParams != null) {
					inputPrice.setText(Utils.separatorAmount(seekParams.progress.toString()))
				}
			}

			override fun onStartTrackingTouch(seekBar: IndicatorSeekBar?) {

			}

			override fun onStopTrackingTouch(seekBar: IndicatorSeekBar?) {

			}

		}
	}
	
	private fun setupInputPrice() {
		val watcher = NumberTextWatcher(
			mBinding.inputPrice,
			",###",
			lifecycleScope
		) { getLowRiskStocks() }
		
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
				appropriateInvestmentFragment.basket.clear()
				appropriateInvestmentFragment.basket.addAll(basket)
				appropriateInvestmentFragment.setup()
			}
			Status.ERROR -> Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
			else -> return
		}
	}
	
}