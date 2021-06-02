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
import kotlinx.android.synthetic.main.fragment_connect_low_risk_brokerage.*

@AndroidEntryPoint
class ConnectLowRiskBrokerageFragment : BaseFragment<ConnectLowRiskBrokerageViewModel>() {
	private lateinit var mBinding: FragmentConnectLowRiskBrokerageBinding
	private val mViewModel: ConnectLowRiskBrokerageViewModel by viewModels()
	private val args: ConnectLowRiskBrokerageFragmentArgs by navArgs()
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
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
		mBinding.viewModel = mViewModel
		mBinding.lifecycleOwner = this
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		mBinding.toolbar.backButton.setOnClickListener {
			findNavController().popBackStack()
		}
		mBinding.addInvestBtn.setOnClickListener {
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
		txtDescBrokerage.setOnClickListener {
			openUrl("https://reg.irfarabi.com/reg/?j=1&is=1&ref=10112")
		}
		mBinding.cardAccount.wealthValue.text = Utils.separatorAmount(args.SelectedPrice)
		mBinding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
			override fun onTabReselected(tab: TabLayout.Tab?) {

			}

			override fun onTabUnselected(tab: TabLayout.Tab?) {

			}

			override fun onTabSelected(tab: TabLayout.Tab?) {
				when(mBinding.tabLayout.selectedTabPosition) {
					0 -> {
						mViewModel.tabCheckedIsSoodinow.set(true)
						mBinding.addInvestBtn.isEnabled = false
						mBinding.addInvestBtn.setBackgroundResource(R.drawable.bg_button_gray)
					}
					1 -> {
						mBinding.addInvestBtn.isEnabled = true
						mBinding.addInvestBtn.setBackgroundResource(R.drawable.bg_button_green)
						mViewModel.tabCheckedIsSoodinow.set(false)
					}
				}
			}

		})
		mBinding.tabLayout.getTabAt(1)?.select()
	}
	

	
	override val baseViewModel: BaseViewModel
		get() = mViewModel
}