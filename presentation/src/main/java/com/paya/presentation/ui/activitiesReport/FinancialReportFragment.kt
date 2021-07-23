package com.paya.presentation.ui.activitiesReport

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentFinancialReportBinding
import com.paya.presentation.ui.activitiesReport.adapter.FinancialReportAdapter
import com.paya.presentation.ui.activitiesReport.dlalog.FinancialLogDetailDialog
import com.paya.presentation.ui.activitiesReport.enum.TypeInvestment
import com.paya.presentation.utils.ListLoadStateAdapter
import com.paya.presentation.viewmodel.FinancialReportViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_financial_report.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val KEY_DATE = "date_key"

@AndroidEntryPoint
class FinancialReportFragment : BaseFragment<FinancialReportViewModel>() {
    private val mViewModel: FinancialReportViewModel by viewModels()
    private var mBinding: FragmentFinancialReportBinding? = null
    private var adapter: FinancialReportAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentFinancialReportBinding.inflate(
            inflater,
            container,
            false
        )
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding?.apply {
            toolbar.backClick = {
                findNavController().popBackStack()
            }
            calenderbtn.setOnClickListener {
                getFindViewController()?.navigate(
                    R.id.selectDateFilterFragment,
                    SelectDateFilterFragment.newBundle(mViewModel.dateFrom, mViewModel.dateTo)
                )
            }
        }
        adapter = FinancialReportAdapter {
            val dialog = FinancialLogDetailDialog()
            dialog.item = it
            dialog.show(parentFragmentManager, "financial dialog")
        }
        adapter?.apply {
            financialRecyclerView.layoutManager = LinearLayoutManager(context)
            financialRecyclerView.adapter =
                withLoadStateHeaderAndFooter(ListLoadStateAdapter(), ListLoadStateAdapter())
            findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<SelectDateFilterFragment.DateFilter>(
                KEY_DATE
            )
                ?.let {
                    it.observe(viewLifecycleOwner) { dateItem ->
                        mViewModel.dateFrom = dateItem.dateFrom
                        mViewModel.dateTo = dateItem.dateTo
                        refresh()
                    }
                }

            viewLifecycleOwner.lifecycleScope.launch {
                mViewModel.status.collectLatest {
                    submitData(it)
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                loadStateFlow.collectLatest {
                    when (it.refresh) {
                        is LoadState.Error -> Toast.makeText(
                            context,
                            (it.refresh as LoadState.Error).error.message, Toast.LENGTH_SHORT
                        ).show()
                        else -> return@collectLatest
                    }
                }
            }
        }
        initFilterTab()


    }

    private fun initFilterTab() {
        mBinding?.apply {
            tabLayout.getTabAt(2)?.let { it.select() }
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tabLayout.selectedTabPosition) {
                        0 -> mViewModel.type = TypeInvestment.Reduction.name

                        1 -> mViewModel.type = TypeInvestment.Increase.name

                        2 -> mViewModel.type = ""
                    }
                    adapter?.apply{refresh()}
                }

            })
        }
    }

    override fun onDestroyView() {
        mBinding = null
        adapter = null
        super.onDestroyView()
    }
    companion object {
        @JvmStatic
        fun setParamDate(
            navController: NavController,
            dateFilter: SelectDateFilterFragment.DateFilter
        ) {
            navController.previousBackStackEntry?.savedStateHandle?.set(KEY_DATE, dateFilter)
        }
    }

    override val baseViewModel: BaseViewModel
        get() = mViewModel

}