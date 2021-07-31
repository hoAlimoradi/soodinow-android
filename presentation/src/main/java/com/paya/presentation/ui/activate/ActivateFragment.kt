package com.paya.presentation.ui.activate


import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentActivateBinding
import com.paya.presentation.utils.*
import com.paya.presentation.viewmodel.ActivateViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ActivateFragment : BaseFragment<ActivateViewModel>() {

	private val mViewModel: ActivateViewModel by viewModels()
	private var mBinding: FragmentActivateBinding? = null
	private val args by navArgs<ActivateFragmentArgs>()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = FragmentActivateBinding.inflate(
			inflater,
			container,
			false
		)

		mViewModel.phoneNumber = args.phoneNumber
		args.title?.let { mViewModel.setTitle(it) }
		return mBinding?.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		observe(mViewModel.status, ::checkActivateStatus)
		observe(mViewModel.remainingTime, ::readyRemainingTime)
		mBinding?.apply {
			nationalCodeInput.requestKeyBoard()
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
				if (nationalCodeInput.getText().isEmpty()) {
					nationalCodeInput.setError("کد ملی خود را وارد کنید")
					return@setOnClickListener
				}
				if (!nationalCodeInput.getText().isNationalCode()) {
					nationalCodeInput.setError("کد ملی وارد شده اشتباه است")
					return@setOnClickListener
				}
				txtPinEntry.text?.let {
					mViewModel.activate(nationalCodeInput.getText(),it.toString())
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

	private fun checkActivateStatus(activateResource: Resource<Any>) {
		if (activateResource.status == Status.SUCCESS) {
			val bundle = Bundle()
			bundle.putString("title", args.title)
			findNavController().navigate(
				R.id.navigateCompletePasswordFragment,
				bundle
			)
		}
	}

	override fun onDestroyView() {
		mBinding = null
		super.onDestroyView()
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
		/*mBinding?.apply {
			txtPinEntry.setText(otp)
			txtPinEntry.hideKeyBoard()
		}*/
	}




}