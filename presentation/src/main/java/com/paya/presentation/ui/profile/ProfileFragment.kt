package com.paya.presentation.ui.profile

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
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
import com.paya.presentation.ui.hint.fragments.CardAccount
import com.paya.presentation.ui.model.PieChartModel
import com.paya.presentation.ui.profile.adapter.EfficiencyAdapter
import com.paya.presentation.ui.profile.dialog.ChartProfileDialog
import com.paya.presentation.ui.publicDialog.NotificationEmptyDialog
import com.paya.presentation.utils.*
import com.paya.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileViewModel>() {

    private lateinit var mBinding: FragmentProfileBinding
    private lateinit var adapter: SlidePagerAdapter
    private val viewModel: ProfileViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_profile,
            container,
            false
        )

        mBinding.lifecycleOwner = this
        mBinding.viewModel = viewModel

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        setupPieChart()
        initChartLabelRecyclerView()
        observe(viewModel.existAccount, ::onExistAccountReady)
        observe(viewModel.profile, ::onProfileReady)
        observe(viewModel.pieChartStatus, ::setData)

        mBinding.historyLogBtn.setOnClickListener {
            getFindViewController()?.navigate(R.id.financialReportFragment)
        }
        viewModel.getExistAccount()

        mBinding.chartEfficiencyBtn.setOnClickListener {
            openChart()
        }
        mBinding.alarm.setOnClickListener {
            NotificationEmptyDialog().show(parentFragmentManager, "notification dialog")
        }

    }

    private fun openChart() {
        var dialog = ChartProfileDialog()
        dialog.boxId = viewModel.currentBoxId
        dialog.show(parentFragmentManager, "chartDialog")
    }

    private fun setupEfficiencyAdapter(list: MutableList<EfficiencyRepoModel>) {
        context?.let { context ->
            /*  val verticalDivider = WithoutLastDividerItemDecorator(context, RecyclerView.VERTICAL)
              ContextCompat.getDrawable(context, R.drawable.divider_efficiency_vertical)
                  ?.let { divider ->
                      verticalDivider.setDrawable(
                          divider
                      )
                  }*/
            val horizontalDivider =
                WithoutLastDividerItemDecorator(context, RecyclerView.HORIZONTAL)
            ContextCompat.getDrawable(context, R.drawable.divider_efficiency_horizontal)
                ?.let { divider ->
                    horizontalDivider.setDrawable(
                        divider
                    )
                }

//            mBinding.efficiencyRecyclerView.addItemDecoration(verticalDivider)
            mBinding.efficiencyRecyclerView.addItemDecoration(horizontalDivider)
            mBinding.efficiencyRecyclerView.adapter = EfficiencyAdapter(list.sortedBy { it.position })

        }
    }


    private fun onExistAccountReady(resource: Resource<ExitAccountRepoModel>) {
        if (resource.status == Status.SUCCESS) {
            viewModel.cardAccounts.clear()
            resource.data?.activeBox?.let {

                it.forEach { activeBox ->
                    viewModel.cardAccounts.add(CardAccount.newInstance(activeBox))
                }
                adapter.notifyDataSetChanged()
                if (it.isEmpty()) {
                    viewModel.setErrorMessage("شما حساب فعال ندارید \n" +
                            "ابتدا پیمان خود را با ما ببندید")
                    return@let
                }
                viewModel.currentBoxId = it.first().id
                viewModel.getProfile(
                    it.first().id
                )
            }
        } else  {
            resource.message?.let { viewModel.setErrorMessage(it) }
        }

    }

    private fun onProfileReady(resource: Resource<MutableList<EfficiencyRepoModel>>) {

        if (resource.status == Status.SUCCESS) {
            resource.data?.let { list ->
                mBinding.parentView.visibility = View.VISIBLE
                setupEfficiencyAdapter(list)
            }

        } else {
            resource.message?.let { viewModel.setErrorMessage(it) }
        }
    }

    private fun setupViewPager() {
        adapter = SlidePagerAdapter(this)
        mBinding.pager.offscreenPageLimit = 1
        mBinding.pager.adapter = adapter
        mBinding.pager.setPadding(
            resources.getDimension(R.dimen.viewpager_item_padding).toInt(),
            0,
            resources.getDimension(R.dimen.viewpager_item_padding).toInt(), 0
        )
        mBinding.pager.setPageTransformer(ViewPagerUtil.getTransformer(requireContext().resources))
        mBinding.pager.addItemDecoration(ViewPagerUtil.getItemDecoration(requireContext()))

        mBinding.pager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    viewModel.cardAccounts[position].activeBoxRepo?.let {
                        viewModel.currentBoxId = it.id
                        viewModel.getProfile(
                            it.id
                        )
                        super.onPageSelected(position)
                    }
                }
            }
        )

    }

    private fun setupPieChart() {
        with(mBinding.pieChart) {
            setUsePercentValues(true)
            description.isEnabled = false
            context?.let { context ->
                setNoDataText(context.getString(R.string.no_data_chart))
                setNoDataTextColor(ContextCompat.getColor(context,R.color.japanese_laurel_green))
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
            with(mBinding.pieChart) {
                this.data = data
                // undo all highlights
                highlightValues(null)
                notifyDataSetChanged()
            }
        }
        setupChartLabelRecyclerView(pieChartModel)

    }

    private fun initChartLabelRecyclerView() {
        mBinding.chartLabelRecyclerView.layoutManager =
            RtlGridLayoutManager(context, 4)
        context?.let { context ->
            val divider = WithoutLastDividerItemDecorator(context, RecyclerView.HORIZONTAL)
            val dividerVertical = WithoutLastDividerItemDecorator(context, RecyclerView.VERTICAL)
            ContextCompat.getDrawable(
                context,
                R.drawable.chart_divider
            )?.let {
                divider.setDrawable(it)
                dividerVertical.setDrawable(it)
            }
            mBinding.chartLabelRecyclerView.removeAllDecoration()
            mBinding.chartLabelRecyclerView.addItemDecoration(divider)
            mBinding.chartLabelRecyclerView.addItemDecoration(dividerVertical)
        }
        if (!viewModel.cardAccounts.isEmpty()) {
            viewModel.cardAccounts.clear()
            adapter.notifyDataSetChanged()
        }
    }

    private fun setupChartLabelRecyclerView(pieChartModel: PieChartModel) {
        val adapter = ChartLabelAdapter(pieChartModel.chartLabels) {
            val y = mBinding.pieChart.data.dataSets[0].getEntryForIndex(it).y
            mBinding.pieChart.highlightValue(it.toFloat(), y, 0)
        }

        mBinding.chartLabelRecyclerView.adapter = adapter

    }

    override fun farabiAccessToken() {
        viewModel.getExistAccount()
    }

    private inner class SlidePagerAdapter(f: Fragment) : FragmentStateAdapter(f) {
        override fun getItemCount(): Int = viewModel.cardAccounts.size

        override fun createFragment(position: Int): Fragment = viewModel.cardAccounts[position]
    }


    override val baseViewModel: BaseViewModel
        get() = viewModel

}