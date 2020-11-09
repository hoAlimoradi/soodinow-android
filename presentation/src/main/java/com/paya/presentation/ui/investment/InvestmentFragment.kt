package com.paya.presentation.ui.investment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentInvestmentBinding
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams
import kotlinx.android.synthetic.main.fragment_investment.*
import java.util.*

class InvestmentFragment : Fragment() {
	
	private lateinit var mBinding: FragmentInvestmentBinding
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_investment,container,false)
		
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		mBinding.submitBtn.setOnClickListener {
			findNavController().navigate(
				InvestmentFragmentDirections.navigateToFirstInformation()
			)
		}
		
		seekBar.onSeekChangeListener = object : OnSeekChangeListener {
			override fun onSeeking(seekParams: SeekParams?) {
			
			}
			
			override fun onStartTrackingTouch(seekBar: IndicatorSeekBar?) {
			
			}
			
			override fun onStopTrackingTouch(seekBar: IndicatorSeekBar?) {
				val min = 0
				val max = 100
				var gold = (Random().nextInt(max - min + 1) + min).toFloat()
				progressGold.setValue(gold)
				txtGold.text = "" + gold.toInt() +" درصد صندوق طلا"
				var divdend = (Random().nextInt(max - min + 1) + min).toFloat()
				progressDividend.setValue(divdend)
				txtDividend.text = "" + divdend.toInt() +" درصد صندوق سهام"
				var income = (Random().nextInt(max - min + 1) + min).toFloat()
				`progressBarIncomeّ`.setValue(income)
				txtIncome.text = "" + income.toInt() +" درصد صندوق درامد ثابت"
			}
			
		}
	}
	
}