package com.paya.presentation.ui.introduceFriends

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.paya.presentation.databinding.FragmentIntroduceFirendsBinding


class IntroduceFriendsFragment : Fragment() {

	
	private  var mBinding: FragmentIntroduceFirendsBinding? = null
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
	}
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding =  FragmentIntroduceFirendsBinding.inflate(inflater,container,false)
		
		return mBinding?.root
	}

	override fun onDestroyView() {
		mBinding = null
		super.onDestroyView()
	}
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		mBinding?.apply {
			toolbar.backClick = {
				findNavController().popBackStack()
			}
		}
	}
	
}