package com.paya.presentation.ui.hint

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentHintBinding
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.HintViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HintFragment : Fragment() {
	
	private lateinit var mBinding: FragmentHintBinding
	private val mViewModel: HintViewModel by viewModels()
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = DataBindingUtil.inflate(
			inflater,
			R.layout.fragment_hint,
			container,
			false
		)
		
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		observe(mViewModel.userState, ::checkUserStatus)
		mBinding.register.setOnClickListener {
			findNavController().navigate(R.id.navigateToRegisterFragment)
		}
		mBinding.login.setOnClickListener {
			findNavController().navigate(R.id.navigateFromHintToLoginFragment)
		}
	}
	
	private fun checkUserStatus(userStatus: HintViewModel.UserState){
		when(userStatus){
			HintViewModel.UserState.IS_HINT_SHOWED -> {
				findNavController().navigate(R.id.navigateToRegisterFragment)
			}
			HintViewModel.UserState.IS_PASSWORD_SET -> {
				findNavController().navigate(R.id.navigateFromHintToLoginFragment)
			}
			HintViewModel.UserState.NONE -> return
		}
	}
	
}