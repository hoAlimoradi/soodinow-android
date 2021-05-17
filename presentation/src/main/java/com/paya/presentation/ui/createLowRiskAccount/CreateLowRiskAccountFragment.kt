package com.paya.presentation.ui.createLowRiskAccount

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.paya.domain.models.repo.IsInRiskListRepoModel
import com.paya.domain.models.repo.PercentRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentCreateLowRiskAccountBinding
import com.paya.presentation.ui.investment.AppropriateInvestmentFragment
import com.paya.presentation.utils.BindingAdapters
import com.paya.presentation.utils.observe
import com.paya.presentation.utils.shared.Point
import com.paya.presentation.viewmodel.CreateLowRiskAccountViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateLowRiskAccountFragment : BaseFragment<CreateLowRiskAccountViewModel>() {

    private val mViewModel: CreateLowRiskAccountViewModel by viewModels()
    private lateinit var mBinding: FragmentCreateLowRiskAccountBinding
    private lateinit var appropriateInvestmentFragment: AppropriateInvestmentFragment

    private var percents: PercentRepoModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_create_low_risk_account,
            container,
            false
        )

        mBinding.lifecycleOwner = this
        mBinding.viewModel = mViewModel

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLowRiskStocks()
        appropriateInvestmentFragment =
            childFragmentManager.findFragmentById(R.id.appropriate_investment_fragment) as AppropriateInvestmentFragment
        observe(mViewModel.lowRiskResource, ::onReady)
        setupInputPrice()
        mBinding.toolbar.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        mBinding.submitBtn.setOnClickListener {
            val price = mBinding.inputPrice.getPriceLong()
            if (price <= 0) {
                Toast.makeText(
                    requireContext(), getString(R.string.price_error), Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            findNavController().navigate(
                CreateLowRiskAccountFragmentDirections.navigationToConnectLowRiskBrokerage(
                    mBinding.inputPrice.getPriceLong(),
                    ("no_risk")
                )
            )
        }

    }

    private fun setupInputPrice() {
        mBinding.inputPrice.setupWatcherPrice(lifecycleScope) {
            getLowRiskStocks()
        }
    }

    private fun getLowRiskStocks() {
        val price = mBinding.inputPrice.getPriceLong()
        if (price <= 0) {
            Toast.makeText(
                requireContext(), getString(R.string.price_error), Toast.LENGTH_SHORT
            ).show()
            return
        }
        mViewModel.getLowRiskStocks("no_risk", price)
    }

    private fun onReady(resource: Resource<IsInRiskListRepoModel>) {
        when (resource.status) {
            Status.SUCCESS -> {
                val response = resource.data?.percent ?: return
                percents = response
                val chartData = resource.data?.chart ?: return
                val mainChartPoints = mutableListOf<Point>()
                chartData.forEachIndexed { index, value ->
                    mainChartPoints.add(
                        Point(
                            index.toFloat(),
                            value,
                            value,
                            value.toLong()
                        )
                    )
                }
                BindingAdapters.setLineAccountChartData(
                    mBinding.chart,
                    mainChartPoints,
                    chartColor = ContextCompat.getColor(
                        requireContext(),
                        R.color.japanese_laurel_green
                    ),
                    markerColor = ContextCompat.getColor(requireContext(), R.color.conifer_green),
                    markerTitleColor = Color.WHITE,
                    chartAlpha = 0,
                    markerType = 0,
                    touchEnabled = true
                )
                val basket = resource.data?.basket ?: return
                appropriateInvestmentFragment.pieChartDataList.clear()
                appropriateInvestmentFragment.pieChartDataList.addAll(
                    basket.map {
                        AppropriateInvestmentFragment.PieChartData(
                            it.percent,
                            it.namad,
                            it.color
                        )
                    }
                )
                appropriateInvestmentFragment.setup()
            }

            else -> return
        }
    }

    override val baseViewModel: BaseViewModel
        get() = mViewModel

}