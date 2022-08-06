package com.paya.presentation.ui.invesmtWallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.paya.domain.models.repo.CashWithdrawRequestRepoModel
import com.paya.domain.models.repo.InvestingInfoRepoModel
import com.paya.domain.models.repo.PortalBankRepoModel
import com.paya.domain.models.repo.WalletChargeRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentInvestlWalletBinding
import com.paya.presentation.ui.invesmtWallet.adapter.BankPortalAdapter
import com.paya.presentation.utils.Utils
import com.paya.presentation.utils.observe
import com.paya.presentation.utils.openUrl
import com.paya.presentation.viewmodel.InvestWalletViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InvestWalletFragment : BaseFragment<InvestWalletViewModel>() {

    private val mViewModel: InvestWalletViewModel by viewModels()
    private var mBinding: FragmentInvestlWalletBinding? = null
    private var bankPortalAdapter: BankPortalAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentInvestlWalletBinding.inflate(inflater, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(mViewModel.investingInfoResource,::investingInfo)
        observe(mViewModel.bankPortalResource,::bankPortal)
        observe(mViewModel.chargeResource,::chargeReady)
        observe(mViewModel.cashWithdrawRequestResource,::withdrawReady)
        mBinding?.apply {
            inputPrice.setPrice("0")
            toolbar.backClick = {
                getFindViewController()?.popBackStack()
            }
            managerAccountHistory.setOnClickListener {
                findNavController().navigate(
                    R.id.financialReportFragment
                )
            }


            submitBtn.setOnClickListener {
                if (inputPrice.getPriceLong() == 0L) {
                    mViewModel.showError("مبلغ مورد نظر خود را وارد کنید")
                    return@setOnClickListener
                }
                if (tabLayout.selectedTabPosition == 1) {
                    mViewModel.charge(
                        inputPrice.getPriceLong(),
                        bankPortalAdapter?.selectionItem ?: ""
                    )
                } else {
                    mViewModel.withdraw(inputPrice.getPriceLong())
                }
            }
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tabLayout.selectedTabPosition) {
                        0 -> {
                            titlePrice.text = getString(R.string.withdrawal_price)
                            recyclerViewBankPortal.visibility = View.GONE
                        }
                        1 -> {
                            titlePrice.text = getString(R.string.deposit_price)
                            recyclerViewBankPortal.visibility = View.VISIBLE
                        }
                    }

                }

            })
            changeTabColor()
            tabLayout.getTabAt(1)?.select()
        }
    }

    private fun investingInfo(resource: Resource<InvestingInfoRepoModel>) {
        if (resource.status == Status.SUCCESS){
            resource.data?.let {
                mBinding?.apply {
                    walletValue.text = Utils.separatorAmount(it.totalMoney)
                }
            }
        }
    }
    private fun bankPortal(resource: Resource<List<PortalBankRepoModel>>) {
        if (resource.status == Status.SUCCESS){
            resource.data?.let {
                mBinding?.apply {
                   recyclerViewBankPortal.layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
                    bankPortalAdapter = BankPortalAdapter(it) {

                    }
                    recyclerViewBankPortal.adapter = bankPortalAdapter
                }
            }
        }
    }
    private fun chargeReady(resource: Resource<WalletChargeRepoModel>) {
        if (resource.status == Status.SUCCESS){
            resource.data?.let {
               openUrl(it.link)
            }
        }
    }
    private fun withdrawReady(resource: Resource<CashWithdrawRequestRepoModel>) {
        if (resource.status == Status.SUCCESS){
            mViewModel.getInvestingInfo()
           context?.let {
               Toast.makeText(it,"برداشت با موفقیت انجام شد",Toast.LENGTH_SHORT).show()
           }
        }
    }

    private fun changeTabColor() {
        mBinding?.apply {
            val tempLinearLayout1: LinearLayout =
                (tabLayout.getChildAt(0) as LinearLayout).getChildAt(0) as LinearLayout
            tempLinearLayout1.setBackgroundResource(R.drawable.selector_tab_brokerage_red)
            val tempLinearLayout2: LinearLayout =
                (tabLayout.getChildAt(0) as LinearLayout).getChildAt(1) as LinearLayout
            tempLinearLayout2.setBackgroundResource(R.drawable.selector_tab_brokerage)
        }
    }

    override val baseViewModel: BaseViewModel
        get() = mViewModel

}