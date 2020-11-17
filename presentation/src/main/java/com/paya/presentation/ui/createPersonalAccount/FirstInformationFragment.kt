package com.paya.presentation.ui.createPersonalAccount

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentFirstInformationBinding
import com.paya.presentation.utils.setAllOnClickListener
import ir.hamsaa.persiandatepicker.Listener
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.util.PersianCalendar


class FirstInformationFragment : Fragment() {
	private lateinit var mBinding: FragmentFirstInformationBinding
	private var picker: PersianDatePickerDialog? = null
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding =
			DataBindingUtil.inflate(inflater,R.layout.fragment_first_information,container,false)
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		mBinding.insertBtn.setOnClickListener {
			findNavController().navigate(
				FirstInformationFragmentDirections.navigateToConnectBrokerage()
			)
		}
		setDate(PersianCalendar())
		mBinding.birthDate.dateGroup.setAllOnClickListener {
			picker = PersianDatePickerDialog(requireContext())
				.setPositiveButtonString("باشه")
				.setNegativeButton("بیخیال")
				.setTodayButton("امروز")
				.setTodayButtonVisible(true)
				.setInitDate(PersianCalendar())
				.setMaxYear(PersianDatePickerDialog.THIS_YEAR)
				.setMinYear(1300)
				.setActionTextColor(Color.GRAY)
				.setListener(object : Listener {
					override fun onDateSelected(persianCalendar: PersianCalendar) {
						setDate(persianCalendar)
					}
					
					override fun onDismissed() {}
				})
			
			picker!!.show()
		}
	}
	
	private fun setDate(persianCalendar: PersianCalendar) {
		mBinding.birthDate.years.text = persianCalendar.persianYear.toString()
		mBinding.birthDate.months.text = persianCalendar.persianMonthName.toString()
		mBinding.birthDate.day.text = persianCalendar.persianDay.toString()
	}
	
	
}