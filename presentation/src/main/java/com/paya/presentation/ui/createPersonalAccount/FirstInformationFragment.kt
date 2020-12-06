package com.paya.presentation.ui.createPersonalAccount

import android.graphics.Color
import android.os.Bundle
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
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentFirstInformationBinding
import com.paya.presentation.ui.createPersonalAccount.enum.NextPageInformation
import com.paya.presentation.utils.observe
import com.paya.presentation.utils.setAllOnClickListener
import com.paya.presentation.viewmodel.FirstInformationViewModel
import dagger.hilt.android.AndroidEntryPoint
import ir.hamsaa.persiandatepicker.Listener
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.util.PersianCalendar

@AndroidEntryPoint
class FirstInformationFragment : BaseFragment<FirstInformationViewModel>() {
	private val mViewModel: FirstInformationViewModel by viewModels()
	private lateinit var mBinding: FragmentFirstInformationBinding
	private var picker: PersianDatePickerDialog? = null
	private val args by navArgs<FirstInformationFragmentArgs>()
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding =
			DataBindingUtil.inflate(inflater,R.layout.fragment_first_information,container,false)
		mBinding.viewModel = mViewModel
		mBinding.lifecycleOwner = this
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		observe(mViewModel.status,::onReadyUpdateProfile)
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
	
	private fun onReadyUpdateProfile(resource: Resource<ProfileRepoModel>) {
		when (resource.status) {
			Status.SUCCESS -> {
				if (args.nextPage == NextPageInformation.personal)
					findNavController().navigate(
						FirstInformationFragmentDirections.navigateToConnectBrokerage()
					)
				else
					findNavController().popBackStack()
			}
			Status.ERROR -> {
				context.let {
					Toast.makeText(it,resource.message,Toast.LENGTH_SHORT)
						.show()
				}
				
			}
			else -> return
		}
		
	}
	
	private fun setDate(persianCalendar: PersianCalendar) {
		mViewModel.birthDay.set(persianCalendar)
		mBinding.birthDate.years.text = persianCalendar.persianYear.toString()
		mBinding.birthDate.months.text = persianCalendar.persianMonthName.toString()
		mBinding.birthDate.day.text = persianCalendar.persianDay.toString()
	}
	
	override val baseViewModel: BaseViewModel
		get() = mViewModel
	
	
}