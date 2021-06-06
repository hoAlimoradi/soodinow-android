package com.paya.presentation.ui.support

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentSupportBinding
import com.paya.presentation.utils.Utils

class SupportFragment : Fragment() {
	
	private  var mBinding: FragmentSupportBinding? = null
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = FragmentSupportBinding.inflate(inflater, container, false)
		return mBinding?.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		mBinding?.apply {
			toolbar.backClick = {
				findNavController().popBackStack()
			}
			onlineChatLayout.setOnClickListener {
				findNavController().navigate(
					SupportFragmentDirections.navigateToChatFragment()
				)
			}
			pulsator.start()
			context?.let { context ->
				buttonLayout.setOnClickListener {
					Utils.calling(context, getString(R.string.call_support_number))
				}
				callButton.setOnClickListener {
					Utils.calling(context, getString(R.string.call_support_number))
				}
			}
		}

	}

	override fun onDestroyView() {
		mBinding?.apply { pulsator.stop() }
		mBinding = null
		super.onDestroyView()
	}
}