package com.paya.presentation.ui.createPersonalAccount

import android.content.Intent
import android.net.Uri
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.paya.domain.models.repo.AddRiskOrderRepoItem
import com.paya.domain.models.repo.AddRiskOrderRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentConnectLowRiskBrokerageBinding
import com.paya.presentation.ui.createPersonalAccount.adapter.InvestAdapter
import com.paya.presentation.utils.BindingAdapters
import com.paya.presentation.utils.PriceTextWatcher
import com.paya.presentation.utils.Utils
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.ConnectLowRiskBrokerageViewModel
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams
import dagger.hilt.android.AndroidEntryPoint

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

		mBinding.addInvestBtn.setOnClickListener {
			findNavController().navigate(ConnectLowRiskBrokerageFragmentDirections.navigateToCreateAccountRulesFragment(args.SelectedPrice,args.riskState))
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
					}
					1 -> {
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