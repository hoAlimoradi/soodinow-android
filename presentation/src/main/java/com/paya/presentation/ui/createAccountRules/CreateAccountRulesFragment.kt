package com.paya.presentation.ui.createAccountRules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentCreateAccountRulesBinding
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.CreateAccountRulesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateAccountRulesFragment : BaseFragment<CreateAccountRulesViewModel>() {

    private lateinit var mBinding: FragmentCreateAccountRulesBinding
    private val mViewModel: CreateAccountRulesViewModel by viewModels()
    private val args: CreateAccountRulesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_create_account_rules, container, false)
        mBinding.lifecycleOwner = this
        mBinding.viewModel = mViewModel
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(mViewModel.status,::readyAddRiskOrder)
        mBinding.toolbar.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        mBinding.addInvestBtn.setOnClickListener {
            if (!mBinding.rulesCheckBox.isChecked)
            {
                mViewModel.showError(getString(R.string.rule_error))
                return@setOnClickListener
            }
            mViewModel.exitAccount(args.riskState,args.SelectedPrice)
        }
    }

    private fun readyAddRiskOrder(resource: Resource<String>) {
        when (resource.status) {
            Status.SUCCESS -> {
                Toast.makeText(
                    requireContext(),
                    "با موفقیت انجام شد",
                    Toast.LENGTH_SHORT).show()
                findNavController().navigate(
                    CreateAccountRulesFragmentDirections.navigateToHomeFragment()
                )
            }

            else -> return
        }
    }

    override val baseViewModel: BaseViewModel
        get() = mViewModel

}