package com.paya.presentation.ui.introduceFriends

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentIntroduceFirendsBinding


class IntroduceFriendsFragment : Fragment() {

	
	private lateinit var mBinding: FragmentIntroduceFirendsBinding
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
	}
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding =  DataBindingUtil.inflate(inflater,R.layout.fragment_introduce_firends,container,false)
		
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		mBinding.toolbar.backButton.setOnClickListener {
			findNavController().popBackStack()
		}
	}
	
}