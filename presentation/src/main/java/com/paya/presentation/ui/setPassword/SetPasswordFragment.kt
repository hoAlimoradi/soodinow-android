package com.paya.presentation.ui.setPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.paya.domain.models.repo.SetPasswordRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentSetPasswordBinding
import com.paya.presentation.utils.isSecretPassword
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.SetPasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetPasswordFragment : BaseFragment<SetPasswordViewModel>() {
	
	private  var mBinding: FragmentSetPasswordBinding?= null
	private val mViewModel: SetPasswordViewModel by viewModels()
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = FragmentSetPasswordBinding.inflate(
			inflater,
			container,
			false
		)
		return mBinding?.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		observe(mViewModel.setPasswordResource, ::checkSetPasswordStatus)
		mBinding?.apply {
			submitButton.setOnClickListener {
				newPassword.setError("")
				repeatPassword.setError("")
				if (newPassword.getText().isEmpty()) {
					newPassword.setError(getString(R.string.empty_password))
					return@setOnClickListener
				}

				if (!newPassword.getText().isSecretPassword()) {
					newPassword.setError(getString(R.string.secret_password_error))
					return@setOnClickListener
				}
				if (repeatPassword.getText().isEmpty()) {
					repeatPassword.setError(getString(R.string.empty_repeat_password))
					return@setOnClickListener
				}

				if (newPassword.getText() != repeatPassword.getText()){
					repeatPassword.setError(getString(R.string.error_repeat_password))
					return@setOnClickListener
				}
				mViewModel.submit(newPassword.getText())
			}
		}
	}
	
	private fun checkSetPasswordStatus(resource: Resource<SetPasswordRepoModel>){
		if (resource.status == Status.SUCCESS){
			findNavController().navigate(
				R.id.navigateCompletePasswordFragment
			)
		}
	}

	override fun onDestroyView() {
		mBinding = null
		super.onDestroyView()
	}
	override val baseViewModel: BaseViewModel
		get() = mViewModel
	
}