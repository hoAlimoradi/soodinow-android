package com.paya.presentation.ui.profile

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import com.paya.domain.models.repo.EfficiencyRepoModel
import com.paya.domain.models.repo.ExitAccountRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentProfileBinding
import com.paya.presentation.ui.adapter.chartLablel.ChartLabelAdapter
import com.paya.presentation.ui.model.PieChartModel
import com.paya.presentation.ui.profile.adapter.EfficiencyAdapter
import com.paya.presentation.ui.profile.dialog.ChartProfileDialog
import com.paya.presentation.ui.publicDialog.NotificationEmptyDialog
import com.paya.presentation.utils.*
import com.paya.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileViewModel>() {

    private var mBinding: FragmentProfileBinding? = null
    private var adapterSlide: SlidePagerAdapter? = null
    private val viewModel: ProfileViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentProfileBinding.inflate(
            inflater,
            container,
            false
        )
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        setupPieChart()
        initChartLabelRecyclerView()
        observe(viewModel.existAccount, ::onExistAccountReady)
        observe(viewModel.pieChartStatus, ::setData)
        observe(viewModel.errorMessage, ::setError)

        mBinding?.apply {
            historyLogBtn.setOnClickListener {
                getFindViewController()?.navigate(R.id.financialReportFragment)
            }
            chartEfficiencyBtn.setOnClickListener {
                openChart()
            }
            alarm.setOnClickListener {
                NotificationEmptyDialog().show(parentFragmentManager, "notification dialog")
            }
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.getFirstExistAccount()
    }

    private fun setError(error: String) {
        mBinding?.apply {
            emptyLayout.emptyLayout.visibility = if (error.isEmpty()) View.GONE else View.VISIBLE
            emptyLayout.titleTextView.text = error
        }
    }

    private fun openChart() {
        var dialog = ChartProfileDialog()
        dialog.boxId = viewModel.currentBoxId
        dialog.show(parentFragmentManager, "chartDialog")
    }

    private fun setupEfficiencyAdapter(list: List<EfficiencyRepoModel>) {
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


            mBinding?.apply {
                efficiencyRecyclerView.apply {
                    layoutManager = RtlGridLayoutManager(context, 3)
                    addItemDecoration(verticalDivider)
                    addItemDecoration(horizontalDivider)
                    adapter =
                        EfficiencyAdapter(list)
                }
            }

        }
    }


    private fun onExistAccountReady(resource: Boolean) {
        if (resource) {
            adapterSlide?.apply { notifyDataSetChanged() }
            mBinding?.apply { parentView.visibility = View.VISIBLE }
/*
            if (viewModel.cardAccounts.first().activeBoxRepo == null)
                return
*/
           /* viewModel.cardAccounts.first().activeBoxRepo?.efficiencyRepoModel?.let {
                setupEfficiencyAdapter(
                    it
                )
            }*/
//            viewModel.currentBoxId = viewModel.cardAccounts.first().activeBoxRepo?.id ?: 0L
           /* viewModel.getProfile(
                viewModel.cardAccounts.first().activeBoxRepo?.id ?: 0L,
                viewModel.cardAccounts.first().isWallet
            )*/


        }

    }

    private fun setupViewPager() {
        adapterSlide = SlidePagerAdapter(this)
        mBinding?.pager?.apply {
            offscreenPageLimit = 1
            adapterSlide?.let { adapter = it }
            setPadding(
                resources.getDimension(R.dimen.viewpager_item_padding).toInt(),
                0,
                resources.getDimension(R.dimen.viewpager_item_padding).toInt(), 0
            )
            setPageTransformer(ViewPagerUtil.getTransformer(requireContext().resources))
            addItemDecoration(ViewPagerUtil.getItemDecoration(requireContext()))

            registerOnPageChangeCallback(
                object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        viewModel.cardAccounts[position].activeBoxRepo?.let {
                            viewModel.currentBoxId = it.id
                            viewModel.getProfile(
                                it.id,
                                viewModel.cardAccounts[position].isWallet
                            )
                            mBinding?.pager?.apply {
                                post {
                                    setupEfficiencyAdapter(it.efficiencyRepoModel)
                                }
                            }

                            super.onPageSelected(position)
                        }
                    }
                }
            )
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
        mBinding?.pieChart?.apply {
            this.data = null
            clear()
            removeAllViews()
            highlightValues(null)
            notifyDataSetChanged()
        }
        val dataSet = PieDataSet(pieChartModel.entries, "")
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 0f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 3f
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
            data.setValueTextColor(ContextCompat.getColor(it, R.color.green))
            getIranSans(it)?.let { typeFace ->
                data.setValueTypeface(typeFace)
            }
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
            if (viewModel.cardAccounts.isNotEmpty()) {
                viewModel.cardAccounts.clear()
                adapterSlide?.apply { notifyDataSetChanged() }
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

    override fun farabiAccessToken() {
        viewModel.getFirstExistAccount()
    }

    private inner class SlidePagerAdapter(f: Fragment) : FragmentStateAdapter(f) {
        override fun getItemCount(): Int = viewModel.cardAccounts.size

        override fun createFragment(position: Int): Fragment = viewModel.cardAccounts[position]
    }

    override fun onDestroyView() {
        mBinding?.pager?.apply { adapter = null }
        adapterSlide = null
        mBinding = null
        super.onDestroyView()
    }

    override val baseViewModel: BaseViewModel
        get() = viewModel

}