package com.paya.presentation.ui.investment

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.paya.domain.models.repo.ProfileRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentCalculateProfitCapitalBinding
import com.paya.presentation.ui.investment.dialog.SelectedDayInMonthDialogFragment
import com.paya.presentation.utils.PriceTextWatcher
import com.paya.presentation.utils.Utils
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.CalculateProfitCapitalViewModel
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_calculate_profit_capital.*

@AndroidEntryPoint
class CalculateProfitCapitalFragment : Fragment() {
	private val mViewModel: CalculateProfitCapitalViewModel by viewModels()
	private lateinit var mBinding: FragmentCalculateProfitCapitalBinding
	private lateinit var profitMethodFragment: ProfitMethodFragment
	val args: CalculateProfitCapitalFragmentArgs by navArgs()
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = DataBindingUtil.inflate(
			inflater,
			R.layout.fragment_calculate_profit_capital,
			container,
			false
		)
		mBinding.viewModel = mViewModel
		mBinding.lifecycleOwner = this
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		setupSeekBar()
		initInputPrice()
		setupInputAmount()
		setupSelectedDay()
		setupRadioButton()
		observe(mViewModel.statusProfile,::checkProfile)
		profitMethodFragment = childFragmentManager.findFragmentById(R.id.profit_method_invest) as ProfitMethodFragment
		profitMethodFragment.percent = args.Percents
		mBinding.inputPrice.setText(Utils.separatorAmount(args.SelectedPrice.toString()))
		
	}
	
	private fun checkProfile(resource: Resource<ProfileRepoModel>) {
		if (resource.status == Status.SUCCESS) {
			if (!resource.data!!.complete) {
				findNavController().navigate(
					CalculateProfitCapitalFragmentDirections.navigateToFirstInformation()
				)
			}
		} else if (resource.status == Status.ERROR) {
			Toast.makeText(
				requireContext(),resource.message,Toast.LENGTH_SHORT
			).show()
		}
	}
	
	private fun setupSelectedDay() {
		txtSelectDay.setOnClickListener {
			val pop = SelectedDayInMonthDialogFragment(object :
				SelectedDayInMonthDialogFragment.OnSelectedDay {
				override fun day(day: String) {
					txtSelectDay.text = day
				}
				
			})
			val fm = requireActivity().supportFragmentManager
			pop.show(fm,"name")
		}
	}
	
	private fun setupRadioButton() {
		radioButtonDepositAmount.setOnCheckedChangeListener { compoundButton,b ->
			radioButtonWithdrawal.isChecked = !b
		}
		radioButtonWithdrawal.setOnCheckedChangeListener { compoundButton,b ->
			radioButtonDepositAmount.isChecked = !b
		}
	}
	
	private fun setupSeekBar() {
		seekBarPrice.onSeekChangeListener = object : OnSeekChangeListener {
			override fun onSeeking(seekParams: SeekParams?) {
				if (seekParams != null) {
					inputPrice.setText(seekParams.progress.toString())
					inputAmount.setText(seekParams.progress.toString())
				}
			}
			
			override fun onStartTrackingTouch(seekBar: IndicatorSeekBar?) {
			
			}
			
			override fun onStopTrackingTouch(seekBar: IndicatorSeekBar?) {
			
			}
			
		}
	}
	
	
	private fun initInputPrice() {
		inputPrice.addTextChangedListener(
			PriceTextWatcher(inputPrice,
				object : PriceTextWatcher.OnChange {
					override fun change(s: CharSequence?) {
					
					}
					
					override fun afterChange(s: Editable?) {
						if (TextUtils.isEmpty(s))
							return
						seekBarPrice.setProgress(Utils.convertToFloatAmount(s.toString()))
					}
					
				})
		)
	}
	
	
	private fun setupInputAmount() {
		inputAmount.addTextChangedListener(
			PriceTextWatcher(inputAmount,
				object : PriceTextWatcher.OnChange {
					override fun change(s: CharSequence?) {
					
					}
					
					override fun afterChange(s: Editable?) {
						if (TextUtils.isEmpty(s))
							return
						seekBarPrice.setProgress(Utils.convertToFloatAmount(s.toString()))
					}
					
				})
		)
	}
	
}