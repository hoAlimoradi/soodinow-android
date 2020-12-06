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
		setupInvestList()
		setupSeekBar()
		changeView()
		observe(mViewModel.status,::readyAddRiskOrder)
		mBinding.addInvestBtn.setOnClickListener {
			mViewModel.exitAccount(args.riskState,args.SelectedPrice)
		}
		initInputPrice()
		mBinding.inputPrice.setText(Utils.separatorAmount(args.SelectedPrice.toString()))
		mBinding.seekBarPrice.setProgress(args.SelectedPrice.toFloat())
		
	}
	
	private fun readyAddRiskOrder(resource: Resource<List<AddRiskOrderRepoItem>>) {
		when (resource.status) {
			Status.SUCCESS -> {
				Toast.makeText(
					requireContext(),
					resource.data?.get(0)?.ruleAction ?: "با موفقیت انجام شد",
					Toast.LENGTH_SHORT).show()
				findNavController().navigate(
					ConnectLowRiskBrokerageFragmentDirections.navigateToHomeFragment()
				)
			}
			Status.ERROR -> context.let {
				Toast.makeText(it,resource.message,Toast.LENGTH_SHORT).show()
			}
			else -> return
		}
	}
	
	private fun setupSeekBar() {
		mBinding.seekBarPrice.isEnabled = false
		mBinding.seekBarPrice.onSeekChangeListener = object : OnSeekChangeListener {
			override fun onSeeking(seekParams: SeekParams?) {
				if (seekParams != null) {
					//mBinding.inputPrice.setText(seekParams.progress.toString())
				}
			}
			
			override fun onStartTrackingTouch(seekBar: IndicatorSeekBar?) {
			
			}
			
			override fun onStopTrackingTouch(seekBar: IndicatorSeekBar?) {
			
			}
			
		}
	}
	
	private fun initInputPrice() {
		mBinding.inputPrice.addTextChangedListener(
			PriceTextWatcher(mBinding.inputPrice,
				object : PriceTextWatcher.OnChange {
					override fun change(s: CharSequence?) {
					
					}
					
					override fun afterChange(s: Editable?) {
						if (TextUtils.isEmpty(s))
							return
						mBinding.seekBarPrice.setProgress(Utils.convertToFloatAmount(s.toString()))
					}
					
				})
		)
	}
	
	private fun setupInvestList() {
		val manager = LinearLayoutManager(requireContext())
		mBinding.investRecyclerView.layoutManager = manager
		mBinding.investRecyclerView.adapter = InvestAdapter() {
			val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://reg.irfarabi.com/reg/?j=1&is=1"));
			startActivity(browserIntent);
		}
	}
	
	private fun setupSpinner() {
	
	}
	
	
	private fun changeView() {
		BindingAdapters.changeBgRoundPurple(mBinding.yesHaveAccountBtn,true)
		BindingAdapters.changeBgRoundPurple(mBinding.noHaveAccountBtn,false)
		BindingAdapters.showHide(mBinding.haveAccountGroup,true)
		BindingAdapters.showHide(mBinding.investRecyclerView,false)
		mBinding.noHaveAccountBtn.setOnClickListener {
			BindingAdapters.changeBgRoundPurple(mBinding.yesHaveAccountBtn,false)
			BindingAdapters.changeBgRoundPurple(mBinding.noHaveAccountBtn,true)
			BindingAdapters.showHide(mBinding.haveAccountGroup,false)
			BindingAdapters.showHide(mBinding.investRecyclerView,true)
		}
		mBinding.yesHaveAccountBtn.setOnClickListener {
			BindingAdapters.changeBgRoundPurple(mBinding.yesHaveAccountBtn,true)
			BindingAdapters.changeBgRoundPurple(mBinding.noHaveAccountBtn,false)
			BindingAdapters.showHide(mBinding.haveAccountGroup,true)
			BindingAdapters.showHide(mBinding.investRecyclerView,false)
		}
	}
	
	override val baseViewModel: BaseViewModel
		get() = mViewModel
}