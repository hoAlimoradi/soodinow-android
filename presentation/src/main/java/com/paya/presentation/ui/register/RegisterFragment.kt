package com.paya.presentation.ui.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.paya.domain.models.repo.RegisterRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentRegisterBinding
import com.paya.presentation.utils.Utils
import com.paya.presentation.utils.isMobile
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterFragment : BaseFragment<RegisterViewModel>() {

    private val mViewModel: RegisterViewModel by viewModels()
    private  var mBinding: FragmentRegisterBinding? = null
    private val args by navArgs<RegisterFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentRegisterBinding.inflate(
            inflater,
            container,
            false
        )

        args.title?.let { mViewModel.setTitle(it) }
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(mViewModel.registerStatus, ::checkRegisterStatus)
        mBinding?.apply {
            context?.let {context->
                Utils.setSpannableRules( rulesCheckBox,getString(R.string.rules_spannable_description),ContextCompat.getColor(context,R.color.governor_bay_blue),)
            }

            submitButton.setOnClickListener {
                phoneNumberLayout.setError("")
                if (!rulesCheckBox.isChecked) {
                    mViewModel.showError(getString(R.string.rule_error))
                    return@setOnClickListener
                }
                if (phoneNumberLayout.getText().isEmpty()) {
                    phoneNumberLayout.setError("لطفا شماره موبایل را وارد کنید")
                    return@setOnClickListener
                }
                if(!phoneNumberLayout.getText().isMobile()){
                    phoneNumberLayout.setError("شماره موبایل وارد شده اشتباه است")
                    return@setOnClickListener
                }
                mViewModel.register(phoneNumberLayout.getText())
            }
        }
    }

    private fun checkRegisterStatus(registerResource: Resource<RegisterRepoModel>) {
        Log.d("checkRegisterStatus", registerResource.status.toString())
        if (registerResource.status == Status.SUCCESS) {
            val bundle = Bundle()
            bundle.putString("phoneNumber", registerResource.data?.username!!)
            bundle.putString("title", args.title)
            findNavController().navigate(
                R.id.activateFragment,
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

}