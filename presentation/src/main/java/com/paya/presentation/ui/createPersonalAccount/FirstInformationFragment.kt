package com.paya.presentation.ui.createPersonalAccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentFirstInformationBinding


class FirstInformationFragment : Fragment() {
	private lateinit var mBinding: FragmentFirstInformationBinding
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding =
			DataBindingUtil.inflate(inflater,R.layout.fragment_first_information,container,false)
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		mBinding.insertBtn.setOnClickListener {
			findNavController().navigate(
				FirstInformationFragmentDirections.navigateToQuestion()
			)
		}
	}
}