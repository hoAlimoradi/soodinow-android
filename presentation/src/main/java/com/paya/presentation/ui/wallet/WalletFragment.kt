package com.paya.presentation.ui.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.paya.domain.models.repo.SoodinowWalletValueRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.observe
import com.paya.presentation.utils.toPersianSeparatedValue
import com.paya.presentation.viewmodel.WalletViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_wallet.*
import kotlinx.android.synthetic.main.fragment_why_soodinow.backButton

@AndroidEntryPoint
class WalletFragment: BaseFragment<WalletViewModel>() {

    private val viewModel: WalletViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wallet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backButton.setOnClickListener { findNavController().popBackStack() }

        observe(viewModel.soodinowWalletValueRepoModelResourceMutableLiveData, ::walletValue)
        viewModel.getSoodinowWalletValue()
    }

    private fun walletValue(resource: Resource<SoodinowWalletValueRepoModel>) {
        if (resource.status == Status.SUCCESS) {
            val value: Double =  resource.data?.value ?: 0.0
            yourWalletValue.text = value.toInt().toPersianSeparatedValue()
        }
    }

    override fun onDestroyView() {

        super.onDestroyView()
    }
    override fun onDestroy() {
        super.onDestroy()

    }

    override val baseViewModel: BaseViewModel
        get() = viewModel
}