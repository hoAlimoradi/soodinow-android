package com.paya.presentation.ui.setNewPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.paya.domain.models.repo.SetResetPasswordRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentSetNewPasswordBinding
import com.paya.presentation.utils.isSecretPassword
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.SetNewPasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetNewPasswordFragment : BaseFragment<SetNewPasswordViewModel>() {
	
	private  var mBinding: FragmentSetNewPasswordBinding?=null
	private val mViewModel: SetNewPasswordViewModel by viewModels()

	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = FragmentSetNewPasswordBinding.inflate(
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
					newPassword.setError("لطفا رمز عبور را وارد کنید")
					return@setOnClickListener
				}

				if (!newPassword.getText().isSecretPassword()) {
					newPassword.setError("پسورد باید از ۸ کارکتر بیشتر باشد و از حروف بزرگ و کوچک و نماد استفاده شود")
					return@setOnClickListener
				}
				if (repeatPassword.getText().isEmpty()) {
					repeatPassword.setError("لطفا تکرار رمز عبور را وارد کنید")
					return@setOnClickListener
				}

				if (newPassword.getText() != repeatPassword.getText()){
					repeatPassword.setError("رمز عبور با تکرار آن یکسان نیست")
					return@setOnClickListener
				}
				mViewModel.submit(newPassword.getText())
			}
		}
	}
	
	private fun checkSetPasswordStatus(resource: Resource<SetResetPasswordRepoModel>){
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