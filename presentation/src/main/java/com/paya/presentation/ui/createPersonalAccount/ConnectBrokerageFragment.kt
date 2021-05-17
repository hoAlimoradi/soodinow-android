package com.paya.presentation.ui.createPersonalAccount

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentConnectBrokerageBinding
import com.paya.presentation.ui.createPersonalAccount.adapter.InvestAdapter
import com.paya.presentation.utils.BindingAdapters
import com.paya.presentation.utils.PriceTextWatcher
import com.paya.presentation.utils.Utils
import com.paya.presentation.viewmodel.ConnectBrokerageViewModel
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConnectBrokerageFragment : BaseFragment<ConnectBrokerageViewModel>() {
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
		mBinding.addInvestBtn.setOnClickListener {
			findNavController().navigate(
				ConnectBrokerageFragmentDirections.navigateToHomeFragment()
			)
		}
		initInputPrice()
		mBinding.toolbar.backButton.setOnClickListener {
			findNavController().popBackStack()
		}
	}
	
	private fun setupSeekBar() {
		mBinding.seekBarPrice.onSeekChangeListener = object : OnSeekChangeListener {
			override fun onSeeking(seekParams: SeekParams?) {
				if (seekParams != null) {
					mBinding.inputPrice.setText(seekParams.progress.toString())
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
		
		}
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