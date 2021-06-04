package com.paya.presentation.ui.cashManager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentCashHistoryBinding
import com.paya.presentation.ui.cashManager.adapter.HistoryPriceAdapter
import com.paya.presentation.viewmodel.CashHistoryManagerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CashHistoryFragment : BaseFragment<CashHistoryManagerViewModel>() {

private val mViewModel: CashHistoryManagerViewModel by viewModels()
    private lateinit var mBinding: FragmentCashHistoryBinding
    private var historyPriceAdapter = HistoryPriceAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding =  DataBindingUtil.inflate(inflater,R.layout.fragment_cash_history, container, false)
        mBinding.lifecycleOwner = this
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupTabLayout()
        mBinding.toolbar.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupTabLayout() {

        val tabDate =  mBinding.filterTabLayout.newTab()
        tabDate.text = "بازه زمانی"
        val tabWithdrawal =  mBinding.filterTabLayout.newTab()
        tabWithdrawal.text = "برداشت ها"
        val tabDeposit =  mBinding.filterTabLayout.newTab()
        tabDeposit.text = "واریزی ها"
        val tabAll =  mBinding.filterTabLayout.newTab()
        tabAll.text = "همه"
        mBinding.filterTabLayout.addTab(tabAll,0,true)
        mBinding.filterTabLayout.addTab(tabDeposit,1,false)
        mBinding.filterTabLayout.addTab(tabWithdrawal,2,false)
        mBinding.filterTabLayout.addTab(tabDate,3,false)





    }

    private fun setupAdapter() {
        val dividerItemDecoration = DividerItemDecoration(context, LinearLayout.VERTICAL)
        context?.let { it ->
            ContextCompat.getDrawable(it,R.drawable.divider_line)?.let {
                dividerItemDecoration.setDrawable(
                    it
                )
            }
        }

        mBinding.recyclerViewReportCash.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = historyPriceAdapter
            addItemDecoration(dividerItemDecoration)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            mViewModel.status.collectLatest {
                historyPriceAdapter.submitData(it)
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }
    override val baseViewModel: BaseViewModel
        get() = mViewModel


}