package com.paya.presentation.ui.activitiesReport.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
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
import com.paya.presentation.utils.ListLoadStateAdapter
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.CalculateProfitCapitalViewModel
import com.paya.presentation.viewmodel.FinancialReportViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_financial_report.*
import kotlinx.android.synthetic.main.fragment_new_card_account.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
        financialRecyclerView.layoutManager = LinearLayoutManager(context)
        financialRecyclerView.adapter = adapter.withLoadStateHeaderAndFooter(ListLoadStateAdapter(),ListLoadStateAdapter())
        viewLifecycleOwner.lifecycleScope.launch {
            mViewModel.status.collectLatest {
                adapter.submitData(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest {
                when(it.refresh) {
                    is LoadState.Error -> Toast.makeText(context,
                        (it.refresh as LoadState.Error).error.message,Toast.LENGTH_SHORT).show()
                    else -> return@collectLatest
                }
            }
        }

    }

    override val baseViewModel: BaseViewModel
        get() = mViewModel

}