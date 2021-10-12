package com.paya.presentation.ui.createLowRiskAccount

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import com.paya.domain.models.repo.IsInRiskListRepoModel
import com.paya.domain.models.repo.PercentEfficiency
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentCreateLowRiskAccountBinding
//import com.paya.presentation.databinding.FragmentCreateLowRiskAccountBinding
import com.paya.presentation.ui.adapter.chartLablel.ChartLabelAdapter
import com.paya.presentation.ui.model.PieChartModel
import com.paya.presentation.ui.profile.adapter.PercentEfficiencyAdapter
import com.paya.presentation.utils.*
import com.paya.presentation.viewmodel.CreateLowRiskAccountViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateLowRiskAccountFragment : BaseFragment<CreateLowRiskAccountViewModel>() {

    private val mViewModel: CreateLowRiskAccountViewModel by viewModels()
    private var mBinding: FragmentCreateLowRiskAccountBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentCreateLowRiskAccountBinding.inflate(
            inflater,
            container,
            false
        )
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initChartLabelRecyclerView()
        setupPieChart()
        observe(mViewModel.lowRiskResource, ::onReady)
        observe(mViewModel.pieChartStatus, ::setData)
        setupInputPrice()
        mBinding?.apply {
            toolbar.backClick = {
                findNavController().popBackStack()
            }
            submitBtn.setOnClickListener {
                val price = inputPrice.getPriceLong()
                if (price <= 0) {
                    Toast.makeText(
                        requireContext(), getString(R.string.price_error), Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                //TODO
                findNavController().navigate(
                    CreateLowRiskAccountFragmentDirections.navigationToConnectLowRiskBrokerage(
                        inputPrice.getPriceLong(),
                        ("no_risk")
                    )
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        callFirstItem()
    }

    private fun callFirstItem() {
        mViewModel.lowRiskResource.value?.let {
            if (it.data != null)
                return
        }
        getLowRiskStocks()
    }

    private fun setupInputPrice() {
        mBinding?.inputPrice?.apply {
            setupWatcherPrice(lifecycleScope) {
                getLowRiskStocks()
            }
        }
    }

    private fun getLowRiskStocks() {
        val price: Long = mBinding?.inputPrice?.getPriceLong()?.let { it } ?: 0
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
                setupEfficiencyAdapter(response)
            }

            else -> return
        }
    }

    private fun setupEfficiencyAdapter(list: List<PercentEfficiency>) {
        context?.let { context ->
              val verticalDivider = WithoutLastDividerItemDecorator(context, RecyclerView.VERTICAL)
              ContextCompat.getDrawable(context, R.drawable.divider_efficiency_vertical)
                  ?.let { divider ->
                      verticalDivider.setDrawable(
                          divider
                      )
                  }
            val horizontalDivider =
                WithoutLastDividerItemDecorator(context, RecyclerView.HORIZONTAL)
            ContextCompat.getDrawable(context, R.drawable.divider_efficiency_horizontal)
                ?.let { divider ->
                    horizontalDivider.setDrawable(
                        divider
                    )
                }

            mBinding?.efficiencyRecyclerView?.apply {
                layoutManager = RtlGridLayoutManager(context, 3)
                addItemDecoration(horizontalDivider)
                //addItemDecoration(verticalDivider)
                adapter = PercentEfficiencyAdapter(list)
            }

        }
    }

    private fun setupPieChart() {
        mBinding?.pieChart?.apply {
            setUsePercentValues(true)
            description.isEnabled = false
            context?.let { context ->
                setNoDataText(context.getString(R.string.no_data_chart))
                setNoDataTextColor(ContextCompat.getColor(context, R.color.japanese_laurel_green))
            }
            setExtraOffsets(0f, 20f, 0f, 20f)

            dragDecelerationFrictionCoef = 0.95f


            isDrawHoleEnabled = true
            setHoleColor(Color.WHITE)

            setTransparentCircleColor(Color.WHITE)
            setTransparentCircleAlpha(110)

            holeRadius = 75f
            transparentCircleRadius = 76f

            setDrawCenterText(true)

            rotationAngle = 0f

            isRotationEnabled = true
            isHighlightPerTapEnabled = true



            animateY(1400, Easing.EaseInOutQuad)


            legend.isEnabled = false

            // entry label styling
            setEntryLabelColor(Color.WHITE)
            setEntryLabelTextSize(12f)
        }
    }

    private fun setData(pieChartModel: PieChartModel) {
        val dataSet = PieDataSet(pieChartModel.entries, "")
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 0f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 3f
        if (!pieChartModel.chartColor.isEmpty())
            dataSet.colors = pieChartModel.chartColor
        dataSet.valueLinePart1OffsetPercentage = 80f
        dataSet.valueLinePart1Length = 0.5f
        dataSet.valueLinePart2Length = 0.3f
        dataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        //dataSet.selectionShift = 0f;
        context?.let {
            dataSet.valueLineColor = ContextCompat.getColor(it, R.color.green)
            val data = PieData(dataSet)
            data.setValueFormatter(PercentFormatter())
            data.setValueTextSize(12f)
            getIranSans(it)?.let { typeFace ->
                data.setValueTypeface(typeFace)
            }
            data.setValueTextColor(ContextCompat.getColor(it, R.color.green))

            mBinding?.pieChart?.apply {
                this.data = data
                // undo all highlights
                highlightValues(null)
                notifyDataSetChanged()
            }
        }
        setupChartLabelRecyclerView(pieChartModel)

    }

    private fun initChartLabelRecyclerView() {
        mBinding?.chartLabelRecyclerView?.apply {
            layoutManager =
                RtlGridLayoutManager(context, 4)
            context?.let { context ->
                val divider = WithoutLastDividerItemDecorator(context, RecyclerView.HORIZONTAL)
                val dividerVertical =
                    WithoutLastDividerItemDecorator(context, RecyclerView.VERTICAL)
                ContextCompat.getDrawable(
                    context,
                    R.drawable.chart_divider
                )?.let {
                    divider.setDrawable(it)
                    dividerVertical.setDrawable(it)
                }
                removeAllDecoration()
                addItemDecoration(divider)
                addItemDecoration(dividerVertical)
            }
        }
    }

    private fun setupChartLabelRecyclerView(pieChartModel: PieChartModel) {
        val adapterChart = ChartLabelAdapter(pieChartModel.chartLabels) {
            mBinding?.pieChart?.apply {
                val y = data.dataSets[0].getEntryForIndex(it).y
                highlightValue(it.toFloat(), y, 0)
            }
        }
        mBinding?.chartLabelRecyclerView?.apply { adapter = adapterChart }

    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()

    }

    override val baseViewModel: BaseViewModel
        get() = mViewModel

}