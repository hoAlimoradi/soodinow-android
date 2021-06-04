package com.paya.presentation.ui.investment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentInvestmentBinding
import com.paya.presentation.ui.createPersonalAccount.enum.NextPageInformation
import com.paya.presentation.ui.investment.dialog.InvestmentScoreDialog
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
			/*findNavController().navigate(
				InvestmentFragmentDirections.navigateToFirstInformation(NextPageInformation.personal)
			)*/
		}
		
		seekBar.onSeekChangeListener = object : OnSeekChangeListener {
			override fun onSeeking(seekParams: SeekParams?) {
			
			}
			
			override fun onStartTrackingTouch(seekBar: IndicatorSeekBar?) {
			
			}
			
			override fun onStopTrackingTouch(seekBar: IndicatorSeekBar?) {
				val max = 70
				val income = (Random().nextInt(max)).toFloat()
				`progressBarIncomeّ`.setValue(income)
				txtIncome.text = "" + income.toInt() +" درصد صندوق درامد ثابت"
				
				val divdend = Random().nextInt((100 - income).toInt()).toFloat()
				progressDividend.setValue(divdend)
				txtDividend.text = "" + divdend.toInt() +" درصد صندوق سهام"
				val gold = 100 - divdend - income
				progressGold.setValue(gold)
				txtGold.text = "" + gold.toInt() +" درصد صندوق طلا"
				
				val stockLp = stock_chart_view.layoutParams as ConstraintLayout.LayoutParams
				stockLp.matchConstraintPercentHeight = ((divdend + income)  / 100)
				stock_chart_view.layoutParams = stockLp
				
				val incomeLp = income_chart_view.layoutParams as ConstraintLayout.LayoutParams
				incomeLp.matchConstraintPercentHeight = income / 100
				income_chart_view.layoutParams = incomeLp
			}
			
		}
		
		simulation_btn.setOnClickListener {
			val pop = InvestmentScoreDialog()
			val fm = requireActivity().supportFragmentManager
			pop.show(fm,"name")
		}
		
	}
	override fun onDestroy() {
		super.onDestroy()
		mBinding.unbind()
	}
}