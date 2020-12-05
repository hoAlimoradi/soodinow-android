package com.paya.presentation.ui.support

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentSupportBinding
import kotlinx.android.synthetic.main.fragment_support.*

class SupportFragment : Fragment() {
	
	private lateinit var mBinding: FragmentSupportBinding
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding =  DataBindingUtil.inflate(inflater,R.layout.fragment_support,container,false)
		mBinding.lifecycleOwner = this
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		pulsator.start()
		mBinding.onlineChatLayout.setOnClickListener {
			findNavController().navigate(
				SupportFragmentDirections.navigateToChatFragment()
			)
		}
	}
	
}