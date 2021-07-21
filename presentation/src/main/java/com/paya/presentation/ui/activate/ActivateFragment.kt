package com.paya.presentation.ui.activate


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
import com.paya.presentation.utils.Utils
import com.paya.presentation.utils.isNationalCode
import com.paya.presentation.utils.observe
import com.paya.presentation.utils.remainingTime
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