package com.paya.presentation.ui.investment

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.paya.presentation.R
import com.paya.presentation.ui.investment.adapter.SelectedDayInMonth
import com.paya.presentation.ui.investment.dialog.SelectedDayInMonthDialogFragment
import com.paya.presentation.utils.PriceTextWatcher
import com.paya.presentation.utils.Utils
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams
import kotlinx.android.synthetic.main.fragment_calculate_profit_capital.*

class CalculateProfitCapitalFragment : Fragment() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_calculate_profit_capital,container,false)
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		setupSeekBar()
		initInputPrice()
		setupInputAmount()
		setupSelectedDay()
		setupRadioButton()
	}
	
	private fun setupSelectedDay() {
		txtSelectDay.setOnClickListener {
			val pop = SelectedDayInMonthDialogFragment(object : SelectedDayInMonthDialogFragment.OnSelectedDay{
				override fun day(day: String) {
					txtSelectDay.text = day
				}
				
			})
			val fm = requireActivity().supportFragmentManager
			pop.show(fm,"name")
		}
	}
	
	private fun setupRadioButton() {
		radioButtonDepositAmount.setOnCheckedChangeListener { compoundButton, b ->
			radioButtonWithdrawal.isChecked = !b
		}
		radioButtonWithdrawal.setOnCheckedChangeListener { compoundButton, b ->
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