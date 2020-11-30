package com.paya.presentation.ui.changePassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentChangePasswordBinding


class ChangePasswordFragment : Fragment() {

	private lateinit var mBinding: FragmentChangePasswordBinding
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	
	}
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding =  DataBindingUtil.inflate(inflater,R.layout.fragment_change_password,container,false)
		mBinding.lifecycleOwner = this
		return mBinding.root
	}
	
}