package com.paya.presentation.ui.changePhoneNumber


import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentChangePhoneNumberBinding
import com.paya.presentation.utils.*
import com.paya.presentation.viewmodel.ChangePhoneNumberViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ChangePhoneNumberFragment : BaseFragment<ChangePhoneNumberViewModel>() {

    private val mViewModel: ChangePhoneNumberViewModel by viewModels()
    private var mBinding: FragmentChangePhoneNumberBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentChangePhoneNumberBinding.inflate(
            inflater,
            container,
            false
        )

        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(mViewModel.status, ::checkActivateStatus)
        observe(mViewModel.remainingTime, ::readyRemainingTime)
        mBinding?.apply {
            txtPinEntry.requestKeyBoard()
            txtPinEntry.setOnEditorActionListener { v, actionId, event ->
				if ((event.action == KeyEvent.ACTION_DOWN) && (event.keyCode == KeyEvent.KEYCODE_ENTER)) {
					newMobileInput.requestKeyBoard()
					return@setOnEditorActionListener true
				}
				return@setOnEditorActionListener false
			}
            submitButton.setOnClickListener {
                if (newMobileInput.getText().isEmpty()) {
                    newMobileInput.setError("لطفا شماره موبایل را وارد کنید")
                    return@setOnClickListener
                }
                if (!newMobileInput.getText().isMobile()) {
                    newMobileInput.setError("شماره موبایل وارد شده اشتباه است")
                    return@setOnClickListener
                }
                txtPinEntry.text?.let { code ->
                    mViewModel.verify(newMobileInput.getText(), code.toString())
                }
            }
            resendCode.setOnClickListener {
                txtPinEntry.setText("")
                requestHint()
                mViewModel.resetPhone()
            }
            Utils.setTintColor(verificationImg, resendCode.context, R.color.gray)
            submitButton.setBackgroundResource(R.drawable.bg_btn_japanese_laurel_green_corner5_size48_alpha_43)
            submitButton.isEnabled = false
            txtPinEntry.doAfterTextChanged {
                val text = it.toString()
                if (text.length != 5) {
                    Utils.setTintColor(verificationImg, resendCode.context, R.color.gray)
                    submitButton.setBackgroundResource(R.drawable.bg_btn_japanese_laurel_green_corner5_size48_alpha_43)
                    submitButton.isEnabled = false
                } else {
                    Utils.setTintColor(verificationImg, resendCode.context, R.color.green)
                    submitButton.setBackgroundResource(R.drawable.bg_btn_japanese_laurel_green_corner5_size48)
                    submitButton.isEnabled = true
                }

            }
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

    private fun checkActivateStatus(activateResource: Resource<Any>) {
        if (activateResource.status == Status.SUCCESS) {
            mBinding?.apply {
                val bundle = Bundle()
                bundle.putString("phoneNumber", newMobileInput.getText())
                bundle.putString("oldCode", txtPinEntry.text.toString())
                findNavController().navigate(
                    R.id.navigateToActivateChangePhoneNumberFragment,
                    bundle
                )
            }
        }
    }

    override val baseViewModel: BaseViewModel
        get() = mViewModel

    override fun onResume() {
        super.onResume()
        requestHint()
    }

    override fun onOTPReceived(otp: String) {
        super.onOTPReceived(otp)
        mBinding?.apply {
            txtPinEntry.setText(otp)
            txtPinEntry.hideKeyBoard()
        }
    }


}