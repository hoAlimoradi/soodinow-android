package com.paya.presentation.ui.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
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
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterFragment : BaseFragment<RegisterViewModel>() {

    private val mViewModel: RegisterViewModel by viewModels()
    private lateinit var mBinding: FragmentRegisterBinding
    private val args by navArgs<RegisterFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_register,
            container,
            false
        )

        args.title?.let { mViewModel.setTitle(it) }
        mBinding.viewModel = mViewModel
        mBinding.lifecycleOwner = this

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(mViewModel.registerStatus, ::checkRegisterStatus)
        mBinding.submitButton.setOnClickListener {
            if (!mBinding.rulesCheckBox.isChecked) {
				mViewModel.showError(getString(R.string.rule_error))
				return@setOnClickListener
			}
			mViewModel.register()
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

    override val baseViewModel: BaseViewModel
        get() = mViewModel

}