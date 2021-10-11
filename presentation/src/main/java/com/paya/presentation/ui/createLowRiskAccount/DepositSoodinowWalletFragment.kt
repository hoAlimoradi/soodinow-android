package com.paya.presentation.ui.createLowRiskAccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paya.domain.models.repo.*
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentWalletSoodinowDepositBinding
import com.paya.presentation.ui.invesmtWallet.adapter.BankPortalAdapter
import com.paya.presentation.utils.Utils
import com.paya.presentation.utils.observe
import com.paya.presentation.utils.openUrl
import com.paya.presentation.viewmodel.DepositSoodinowWalletViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_investl_wallet.*
import kotlinx.android.synthetic.main.popup_linear_chart.view.*

const val HOST_ID = "hostId"
@AndroidEntryPoint
class DepositSoodinowWalletFragment : BaseFragment<DepositSoodinowWalletViewModel>() {
    private val mViewModel: DepositSoodinowWalletViewModel by viewModels()
    private var binding: FragmentWalletSoodinowDepositBinding? = null

    private var bankPortalAdapter: BankPortalAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWalletSoodinowDepositBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getFirstApi()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(mViewModel.bankPortalResource,::bankPortal)
        observe(mViewModel.walletChargeLiveData,::chargeReady)
        observe(mViewModel.walletValeLiveData,::walletValueReady)
        observe(mViewModel.getPreInvoiceLiveData,::preInvoiceReady)
        observe(mViewModel.walletBuyLiveData,::buyReady)
        observe(mViewModel.viewStateLiveData,::viewStateReady)
        binding?.apply {
            toolbar.backClick = {
                findNavController().popBackStack()
            }
            chargeButton.setOnClickListener {
                if ( mViewModel.chargeValue== 0L) {
                    mViewModel.showError("مبلغ مورد نظر خود را وارد کنید")
                    return@setOnClickListener
                }
                    mViewModel.charge(bankPortalAdapter?.selectionItem ?: "")
            }
            walletButtonButton.setOnClickListener {
                if ( mViewModel.investmentValue== 0L) {
                    mViewModel.showError("مبلغ مورد نظر خود را وارد کنید")
                    return@setOnClickListener
                }
                mViewModel.buy()
            }
        }

    }
    private fun bankPortal(resource: Resource<List<PortalBankRepoModel>>) {
        if (resource.status == Status.SUCCESS){
            resource.data?.let {
                binding?.apply {
                    recyclerViewBankPortal.layoutManager = LinearLayoutManager(context,
                        RecyclerView.HORIZONTAL,false)
                    bankPortalAdapter = BankPortalAdapter(it) {

                    }
                    recyclerViewBankPortal.adapter = bankPortalAdapter
                }
            }
        }
    }

    private fun walletValueReady(resource: Resource<WalletValueRepoModel>) {
        if (resource.status == Status.SUCCESS){
            resource.data?.let {
                binding?.apply {
                    priceWallet.text = Utils.separatorAmount(it.balance)+ " " + getString(R.string.toman)
                    priceWalletLayout.text = Utils.separatorAmount(it.balance)+ " " + getString(R.string.toman)
                }
            }
        }
    }


    private fun preInvoiceReady(resource: Resource<PreInvoiceRepoModel>) {
        if (resource.status == Status.SUCCESS){
            resource.data?.let {
                binding?.apply {
                    priceInvest.text = Utils.separatorAmount(it.price)
                }
            }
        }
    }
    private fun buyReady(resource: Resource<WalletBuyRepoModel>) {
        if (resource.status == Status.SUCCESS){
            resource.message?.let {
               Toast.makeText(context,it,Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun viewStateReady(state: Int) {
        binding?.apply {
            walletLayout.visibility = if (state == 1) View.VISIBLE else View.GONE
            chargeLayout.visibility = if (state == 2) View.VISIBLE else View.GONE
            price.text = Utils.separatorAmount(mViewModel.chargeValue) + " " + getString(R.string.toman)
        }
    }
    private fun chargeReady(resource: Resource<WalletChargeRepoModel>) {
        if (resource.status == Status.SUCCESS){
            resource.data?.let {
                openUrl(it.link)
            }
        }
    }


    override val baseViewModel: BaseViewModel
        get() = mViewModel
}