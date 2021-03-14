package com.paya.presentation.ui.forgotPassword

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.paya.domain.models.repo.RegisterRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentForgotPasswordBinding
import com.paya.presentation.databinding.FragmentRegisterBinding
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.ForgotPasswordViewModel
import com.paya.presentation.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment<ForgotPasswordViewModel>() {
	
	private val mViewModel: ForgotPasswordViewModel by viewModels()
	private lateinit var mBinding: FragmentForgotPasswordBinding

	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = DataBindingUtil.inflate(
			inflater,
			R.layout.fragment_forgot_password,
			container,
			false
		)

		mBinding.viewModel = mViewModel
		mBinding.lifecycleOwner = this
		
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		observe(mViewModel.registerStatus,::checkRegisterStatus)
	}
	
	private fun checkRegisterStatus(registerResource: Resource<RegisterRepoModel>){
		Log.d("checkRegisterStatus",registerResource.status.toString())
		if (registerResource.status == Status.SUCCESS){
			val bundle = Bundle()
			bundle.putString("phoneNumber", registerResource.data?.username!!)
			findNavController().navigate(
				R.id.activateForgotFragment,
				bundle
			)
		}else if (registerResource.status == Status.ERROR){
			registerResource.message?.let { mBinding.phoneNumberLayout.errorLayout.setError(it) }
			Toast.makeText(
				requireContext(),registerResource.message,Toast.LENGTH_SHORT
			).show()
		}
	}
	
	override val baseViewModel: BaseViewModel
		get() = mViewModel
	
}