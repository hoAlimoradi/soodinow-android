package com.paya.presentation.ui.login


import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentActivateLoginBinding
import com.paya.presentation.utils.*
import com.paya.presentation.viewmodel.ActivateLoginViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ActivateLoginFragment : BaseFragment<ActivateLoginViewModel>() {
	
	private val mViewModel: ActivateLoginViewModel by viewModels()
	private  var mBinding: FragmentActivateLoginBinding? = null
	private val args by navArgs<ActivateLoginFragmentArgs>()
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = FragmentActivateLoginBinding.inflate(
			inflater,
			container,
			false
		)
		
		mViewModel.username = args.username
		return mBinding?.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		observe(mViewModel.status, ::checkActivateStatus)
		observe(mViewModel.remainingTime, ::readyRemainingTime)
		mBinding?.apply {
			txtPinEntry.requestKeyBoard()
			txtPinEntry.setOnEditorActionListener { v, actionId, event ->
				if ((event.action == KeyEvent.ACTION_DOWN) && (event.keyCode == KeyEvent.KEYCODE_ENTER)) {
					txtPinEntry.hideKeyBoard()
					return@setOnEditorActionListener true
				}
				return@setOnEditorActionListener false
			}
			changeNumber.setOnClickListener {
				findNavController().popBackStack()
			}
			submitButton.setOnClickListener {
				txtPinEntry.text?.let {
					mViewModel.activate(it.toString())
				}
			}
			resendCode.setOnClickListener {
				txtPinEntry.setText("")
				requestHint()
				mViewModel.register()
			}
			Utils.setVerificationPinImage(txtPinEntry, verificationImg)
		}
	}
	private fun readyRemainingTime(time: Int) {
		mBinding?.apply {
			Utils.enableDisable(resendCode, time)
			resendCodeTime.text = time.remainingTime()
		}

	}
	override fun onDestroyView() {
		mBinding = null
		super.onDestroyView()
	}
	private fun checkActivateStatus(activateResource: Resource<Any>){
		if (activateResource.status == Status.SUCCESS){

			findNavController().navigate(
				R.id.navigateToHomeFragment
			)
		}
	}

	override val baseViewModel: BaseViewModel
		get() = mViewModel

	override fun onResume() {
		super.onResume()
		requestHint()
	}
	override fun onPause() {
		super.onPause()
		activity?.let { activity ->
			if (registerSms != null) {
				activity.unregisterReceiver(registerSms)
				registerSms = null
			}
		}
	}
	override fun onOTPReceived(otp: String) {
		super.onOTPReceived(otp)
		mBinding?.apply {
			txtPinEntry.setText(otp)
			txtPinEntry.hideKeyBoard()
		}
	}


}