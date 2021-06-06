package com.paya.presentation.ui.forgotPassword

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.paya.domain.models.repo.ResetPasswordRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentForgotPasswordBinding
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.ForgotPasswordViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment<ForgotPasswordViewModel>() {

	private val mViewModel: ForgotPasswordViewModel by viewModels()
	private var mBinding: FragmentForgotPasswordBinding? = null


	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = FragmentForgotPasswordBinding.inflate(
			inflater,
			container,
			false
		)
		return mBinding?.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		observe(mViewModel.resetPasswordStatus, ::checkRegisterStatus)
		mBinding?.apply {
			submitButton.setOnClickListener {
				if (phoneNumberLayout.getText().isEmpty()) {
					phoneNumberLayout.setError("لطفا شماره موبایل را وارد کنید")
					return@setOnClickListener
				}
				if (phoneNumberLayout.getText().length != 9) {
					phoneNumberLayout.setError("شماره موبایل وارد شده شده اشتباه است")
					return@setOnClickListener
				}
				mViewModel.resetPassword(phoneNumberLayout.getText())
			}
		}
	}

	override fun onDestroyView() {
		mBinding = null
		super.onDestroyView()
	}

	private fun checkRegisterStatus(registerResource: Resource<ResetPasswordRepoModel>) {
		Log.d("checkRegisterStatus", registerResource.status.toString())
		if (registerResource.status == Status.SUCCESS) {
			val bundle = Bundle()
			bundle.putString("phoneNumber", registerResource.data?.username!!)
			findNavController().navigate(
				R.id.activateForgotFragment,
				bundle
			)
		}
	}
	
	override val baseViewModel: BaseViewModel
		get() = mViewModel
	
}