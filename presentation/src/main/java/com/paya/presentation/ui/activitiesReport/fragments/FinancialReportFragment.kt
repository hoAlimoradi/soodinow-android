package com.paya.presentation.ui.activitiesReport.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.paya.domain.models.repo.InvestmentLogsRepoModel
import com.paya.domain.models.repo.ProfileRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentFinancialReportBinding
import com.paya.presentation.ui.activitiesReport.adapter.FinancialReportAdapter
import com.paya.presentation.ui.createPersonalAccount.FirstInformationFragment
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.CalculateProfitCapitalViewModel
import com.paya.presentation.viewmodel.FinancialReportViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_financial_report.*
import kotlinx.android.synthetic.main.fragment_new_card_account.*

@AndroidEntryPoint
class FinancialReportFragment : BaseFragment<FinancialReportViewModel>() {
    private val mViewModel: FinancialReportViewModel by viewModels()
    private lateinit var mBinding : FragmentFinancialReportBinding
    private val adapter = FinancialReportAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_financial_report,
            container,
            false
        )

        mBinding.viewModel = mViewModel
        mBinding.lifecycleOwner = this

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(mViewModel.status,::readyInvestmentLogs)
        financialRecyclerView.layoutManager = LinearLayoutManager(context)
        financialRecyclerView.adapter = adapter
    }

    private fun readyInvestmentLogs(resource: Resource<List<InvestmentLogsRepoModel>>) {
        if (resource.status == Status.SUCCESS) {
            resource.data?.let { adapter.setItems(it) }
        } else if (resource.status == Status.ERROR) {
            Toast.makeText(
                requireContext(),resource.message, Toast.LENGTH_SHORT
            ).show()
        }
    }

    override val baseViewModel: BaseViewModel
        get() = mViewModel

}