package com.paya.presentation.ui.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.paya.domain.models.repo.InvestingInfoRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentWalletBinding
import com.paya.presentation.ui.wallet.adapter.WalletInvestAdapter
import com.paya.presentation.utils.Utils
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.WalletViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_why_soodinow.*

@AndroidEntryPoint
class WalletFragment : BaseFragment<WalletViewModel>() {

    private val viewModel: WalletViewModel by viewModels()
    private var binding: FragmentWalletBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWalletBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backButton.setOnClickListener { findNavController().popBackStack() }
        binding?.apply {
            walletImageViewConstraintLayout.setOnClickListener {
                getFindViewController()?.navigate(R.id.investWallet)
            }
        }
        observe(viewModel.soodinowWalletValueRepoModelResourceMutableLiveData, ::walletValue)
        viewModel.getSoodinowWalletValue()
    }

    private fun walletValue(resource: Resource<InvestingInfoRepoModel>) {
        if (resource.status == Status.SUCCESS) {
            resource.data?.let { data ->
                binding?.apply {
                    yourWalletValue.text =
                        Utils.separatorAmount(data.totalMoney) + " " + resources.getString(R.string.toman)
                    blockValue.text =
                        Utils.separatorAmount(data.totalBlockMoney) + " " + resources.getString(R.string.toman)
                    investValue.text =
                        Utils.separatorAmount(data.totalProfit) + " " + resources.getString(R.string.toman)
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    recyclerView.adapter = WalletInvestAdapter(data.investmentBoxesData)
                }
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    override val baseViewModel: BaseViewModel
        get() = viewModel
}