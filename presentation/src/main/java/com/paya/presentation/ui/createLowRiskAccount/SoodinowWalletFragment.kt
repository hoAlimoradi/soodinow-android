package com.paya.presentation.ui.createLowRiskAccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.paya.domain.models.repo.SoodinowWalletContractRepoModel
import com.paya.domain.tools.Resource
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.ui.createLowRiskAccount.adapter.SectionItem
import com.paya.presentation.ui.createLowRiskAccount.adapter.SoodinowWalletAdapter
import com.paya.presentation.ui.createLowRiskAccount.adapter.SoodinowWalletItem
import com.paya.presentation.ui.createLowRiskAccount.adapter.SoodinowWalletRecyclerViewItem
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.CreateLowRiskAccountViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_wallet_soodinow.*

@AndroidEntryPoint
class SoodinowWalletFragment : BaseFragment<CreateLowRiskAccountViewModel>() {
    private val mViewModel: CreateLowRiskAccountViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wallet_soodinow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(mViewModel.soodinowWalletRepoModelResource, ::onReady)
        mViewModel.getSoodinowWalletContracts()
    }

    private fun onReady(resource: Resource<List<SoodinowWalletContractRepoModel>>) {

        var list: List<SoodinowWalletRecyclerViewItem> = listOf(SectionItem(title = "firstItem"))

        if (!resource.data.isNullOrEmpty()) {
            resource.data?.forEach {

                list.plus(
                    SoodinowWalletItem(
                        pointTitle = it.pointTitle,
                        name = it.name,
                        description = it.description,
                        trimesterValue = it.trimesterValue,
                        monthlyValue = it.monthlyValue,
                        weeklyValue = it.weeklyValue
                    )
                )
            }
            setupSoodinowWalletAdapter(list)
        }
/*
        when (resource.status) {

            Status.SUCCESS -> {
                val response = resource.data?.percent ?: return
                setupSoodinowWalletAdapter(response)

            }

            else -> return
        }*/
    }


    private fun setupSoodinowWalletAdapter(list: List<SoodinowWalletRecyclerViewItem>) {
        context?.let { context ->
            soodinowWalletRecycleView?.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = SoodinowWalletAdapter(list)
            }
        }
    }

    override val baseViewModel: BaseViewModel
        get() = mViewModel
}