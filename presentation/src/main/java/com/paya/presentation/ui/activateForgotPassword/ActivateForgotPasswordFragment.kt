package com.paya.presentation.ui.activateForgotPassword


import android.os.Bundle
import android.util.Log
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
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentActivateBinding
import com.paya.presentation.databinding.FragmentActivateForgotBinding
import com.paya.presentation.utils.Utils
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.ActivateForgotPasswordViewModel
import com.paya.presentation.viewmodel.ActivateViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ActivateForgotPasswordFragment : BaseFragment<ActivateForgotPasswordViewModel>() {
	
	private val mViewModel: ActivateForgotPasswordViewModel by viewModels()
	private  var mBinding: FragmentActivateForgotBinding? = null
	private val args by navArgs<ActivateForgotPasswordFragmentArgs>()
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = FragmentActivateForgotBinding.inflate(
			inflater,
			container,
			false
		)
		
		mViewModel.phoneNumber = args.phoneNumber
		return mBinding?.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		observe(mViewModel.status, ::checkActivateStatus)
		observe(mViewModel.remainingTime, ::readyRemainingTime)
		mBinding?.apply {
			changeNumber.setOnClickListener {
				findNavController().popBackStack()
			}
			submitButton.setOnClickListener {
				txtPinEntry.text?.let {
					mViewModel.activate(it.toString())
				}
			}
			resendCode.setOnClickListener {
				mViewModel.register()
			}
			Utils.setVerificationPinImage(txtPinEntry, verificationImg)
		}
		requestHint()
	}
	private fun readyRemainingTime(time: Int) {
		mBinding?.apply {
			Utils.enableDisable(resendCode, time)
			resendCodeTime.text = "00:$time"
		}

	}
	override fun onDestroyView() {
		mBinding = null
		super.onDestroyView()
	}
	private fun checkActivateStatus(activateResource: Resource<Any>){
		if (activateResource.status == Status.SUCCESS){

			findNavController().navigate(
				R.id.navigateToSetNewPasswordFragment
			)
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