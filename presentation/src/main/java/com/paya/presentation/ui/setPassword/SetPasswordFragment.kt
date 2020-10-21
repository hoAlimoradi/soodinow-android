package com.paya.presentation.ui.setPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentSetPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetPasswordFragment : Fragment() {
	
	private lateinit var mBinding: FragmentSetPasswordBinding
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = DataBindingUtil.inflate(
			inflater,
			R.layout.fragment_set_password,
			container,
			false
		)
		return mBinding.root
	}
	
}