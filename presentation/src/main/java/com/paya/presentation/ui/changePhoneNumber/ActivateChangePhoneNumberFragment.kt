package com.paya.presentation.ui.changePhoneNumber


import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.paya.domain.models.repo.ResetPhoneVerifyRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentActivateBinding
import com.paya.presentation.databinding.FragmentActivateChangePhoneNumberBinding
import com.paya.presentation.databinding.FragmentActivateForgotBinding
import com.paya.presentation.ui.activateForgotPassword.ActivateForgotPasswordFragmentArgs
import com.paya.presentation.utils.*
import com.paya.presentation.viewmodel.ActivateChangePhoneNumberViewModel
import com.paya.presentation.viewmodel.ActivateForgotPasswordViewModel
import com.paya.presentation.viewmodel.ActivateViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ActivateChangePhoneNumberFragment : BaseFragment<ActivateChangePhoneNumberViewModel>() {
	
	private val mViewModel: ActivateChangePhoneNumberViewModel by viewModels()
	private  var mBinding: FragmentActivateChangePhoneNumberBinding? = null
	private val args by navArgs<ActivateChangePhoneNumberFragmentArgs>()

	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = FragmentActivateChangePhoneNumberBinding.inflate(
			inflater,
			container,
			false
		)
		mViewModel.phoneNumber = args.phoneNumber
		mViewModel.oldCode = args.oldCode
		return mBinding?.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		observe(mViewModel.status, ::checkActivateStatus)
		observe(mViewModel.statusVerify, ::checkResetStatus)
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
					mViewModel.activateResetPhone(it.toString())
				}
			}
			resendCode.setOnClickListener {
				txtPinEntry.setText("")
				mViewModel.register()
			}
			Utils.setVerificationPinImage(txtPinEntry, verificationImg)
		}
		requestHint()
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
				R.id.navigateToCompleteResetPhoneFragment
			)
		}
	}

	private fun checkResetStatus(activateResource: Resource<ResetPhoneVerifyRepoModel>){
		if (activateResource.status == Status.ERROR && activateResource.code == 1032){
			findNavController().popBackStack(R.id.mainFragment,true)
		}
	}
	
	override val baseViewModel: BaseViewModel
		get() = mViewModel

	private fun requestHint() {

		val client = context?.let {
			SmsRetriever.getClient(it /* context */) }

		val task: Task<Void> = client!!.startSmsRetriever()

		task.addOnSuccessListener(OnSuccessListener<Void?> {
			Log.d("","")
			// Successfully started retriever, expect broadcast intent
			// ...
		})

		task.addOnFailureListener(OnFailureListener {
			Log.d("","")
			// Failed to start retriever, inspect Exception for more details
			// ...
		})

	}




}