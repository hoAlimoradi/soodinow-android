package com.paya.presentation.ui.createPersonalAccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentConnectBrokerageBinding
import com.paya.presentation.ui.createPersonalAccount.adapter.InvestAdapter
import com.paya.presentation.utils.BindingAdapters
import com.paya.presentation.viewmodel.ConnectBrokerageViewModel
import com.paya.presentation.viewmodel.LoginViewModel
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConnectBrokerageFragment : Fragment() {
	private lateinit var mBinding: FragmentConnectBrokerageBinding
	private val mViewModel: ConnectBrokerageViewModel by viewModels()
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding =
			DataBindingUtil.inflate(inflater,R.layout.fragment_connect_brokerage,container,false)
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		setupInvestList()
		setupSeekBar()
		changeView()
		
	}
	
	private fun setupInvestList() {
		val manager = LinearLayoutManager(requireContext())
		mBinding.investRecyclerView.layoutManager = manager
		mBinding.investRecyclerView.adapter = InvestAdapter()
	}
	
	private fun setupSeekBar() {
		mBinding.seekBarPrice.onSeekChangeListener = object: OnSeekChangeListener {
			override fun onSeeking(seekParams: SeekParams?) {
				if (seekParams != null) {
					mBinding.inputPrice.setText(BindingAdapters.separatorAmount(seekParams.progress))
				}
			}
			
			override fun onStartTrackingTouch(seekBar: IndicatorSeekBar?) {
			
			}
			
			override fun onStopTrackingTouch(seekBar: IndicatorSeekBar?) {
			
			}
			
		};
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
}