package com.paya.presentation.ui.createLowRiskAccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayout
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentConnectLowRiskBrokerageBinding
import com.paya.presentation.utils.Utils
import com.paya.presentation.utils.openUrl
import com.paya.presentation.viewmodel.ConnectLowRiskBrokerageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConnectLowRiskBrokerageFragment : BaseFragment<ConnectLowRiskBrokerageViewModel>() {
	private var mBinding: FragmentConnectLowRiskBrokerageBinding? = null
	private val mViewModel: ConnectLowRiskBrokerageViewModel by viewModels()
	private val args: ConnectLowRiskBrokerageFragmentArgs by navArgs()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding =
			DataBindingUtil.inflate(
				inflater,
				R.layout.fragment_connect_low_risk_brokerage,
				container,
				false
			)
		mBinding?.apply {
			viewModel = mViewModel
			lifecycleOwner = this@ConnectLowRiskBrokerageFragment
		}
		return mBinding?.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		mBinding?.apply {
			toolbar.backButton.setOnClickListener {
				findNavController().popBackStack()
			}
			addInvestBtn.setOnClickListener {
				mViewModel.tabCheckedIsSoodinow.get()?.let {
					if (it)
						return@setOnClickListener
				}
				findNavController().navigate(
					ConnectLowRiskBrokerageFragmentDirections.navigateToCreateAccountRulesFragment(
						args.SelectedPrice,
						args.riskState
					)
				)
			}
			cardAccount.wealthValue.text = Utils.separatorAmount(args.SelectedPrice)
			txtDescBrokerage.setOnClickListener {
				openUrl("https://reg.irfarabi.com/reg/?j=1&is=1&ref=10112")
			}
			tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
				override fun onTabReselected(tab: TabLayout.Tab?) {

				}

				override fun onTabUnselected(tab: TabLayout.Tab?) {

				}

				override fun onTabSelected(tab: TabLayout.Tab?) {
					when (tabLayout.selectedTabPosition) {
						0 -> {
							mViewModel.tabCheckedIsSoodinow.set(true)
							addInvestBtn.isEnabled = false
							addInvestBtn.setBackgroundResource(R.drawable.bg_button_gray)
						}
						1 -> {
							addInvestBtn.isEnabled = true
							addInvestBtn.setBackgroundResource(R.drawable.bg_button_green)
							mViewModel.tabCheckedIsSoodinow.set(false)
						}
					}
				}

			})
			tabLayout.getTabAt(1)?.select()
		}
	}

	override fun onDestroyView() {
		mBinding = null
		super.onDestroyView()
	}

	override val baseViewModel: BaseViewModel
		get() = mViewModel
}