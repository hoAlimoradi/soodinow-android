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
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.paya.domain.models.repo.BoxHistoryRepoModel
import com.paya.domain.models.repo.ExitAccountRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentProfileBinding
import com.paya.presentation.ui.hint.fragments.CardAccount
import com.paya.presentation.ui.investment.AppropriateInvestmentFragment
import com.paya.presentation.utils.*
import com.paya.presentation.utils.shared.Point
import com.paya.presentation.viewmodel.ProfileViewModel
import com.paya.presentation.viewmodel.ProfileViewModel.FilterProfile.*
import dagger.hilt.android.AndroidEntryPoint
import ir.hamsaa.persiandatepicker.util.PersianCalendar

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
        initRadioGroup()
        observe(viewModel.existAccount, ::onExistAccountReady)
        observe(viewModel.profile, ::onProfileReady)
        mBinding.alarm.setOnClickListener {

        }
        viewModel.getExistAccount()
        context?.let {
            var param = mBinding.pager.layoutParams
            param.height = getCardHeight(it,60f).toInt()
            mBinding.pager.layoutParams = param
        }


    }

    private fun initRadioGroup() {
        mBinding.chartTabLayout.getTabAt(2)?.select()
        mBinding.chartTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            // TODO: 4/2/21 box history Number what?
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewModel.currentBoxId ?: return
                when (mBinding.chartTabLayout.selectedTabPosition) {
                    // day
                    2 -> {
                        viewModel.boxHistoryType = day
                        viewModel.boxHistoryNumber = 3
                    }
                    //week
                    1 -> {
                        viewModel.boxHistoryType = week
                        viewModel.boxHistoryNumber = 1
                    }
                    //month
                    0 -> {
                        viewModel.boxHistoryType = month
                        viewModel.boxHistoryNumber = 1
                    }
                    //years
                    // TODO: 4/26/21 change to years
                    -1 -> {
                        viewModel.boxHistoryType = month
                        viewModel.boxHistoryNumber = 3
                    }
                    else -> {
                        viewModel.boxHistoryType = day
                        viewModel.boxHistoryNumber = 3
                    }
                }
                viewModel.currentBoxId?.let {
                    viewModel.getProfile(it, viewModel.boxHistoryType, viewModel.boxHistoryNumber)
                }
            }

        })

    }

    private fun onExistAccountReady(resource: Resource<ExitAccountRepoModel>) {
        if (resource.status == Status.SUCCESS) {
            viewModel.cardAccounts.clear()
            resource.data?.activeBox?.let {
                viewModel.boxHistoryId = it.map { activeBox -> activeBox.id }
                it.forEach { activeBox ->
                    viewModel.boxHistoryHahMap[activeBox.id] = null
                    viewModel.cardAccounts.add(CardAccount.newInstance(activeBox))
                }
                adapter.notifyDataSetChanged()
                if (it.isEmpty()) {
                    viewModel.setErrorMessage("شما هیچ حسابی ایجاد نکرده اید")
                    return@let
                }
                viewModel.currentBoxId = it.first().id
                viewModel.getProfile(
                    it.first().id,
                    viewModel.boxHistoryType,
                    viewModel.boxHistoryNumber
                )
            }
        }

    }

    private fun onProfileReady(resource: Resource<BoxHistoryRepoModel>) {
        if (resource.status == Status.SUCCESS) {
            viewModel.boxHistoryHahMap[viewModel.currentBoxId!!] = resource.data
            resource.data?.let {
                mBinding.parentView.visibility = View.VISIBLE
                setCurrentBoxData(it)
            }
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
                        val boxModel = viewModel.boxHistoryHahMap[viewModel.currentBoxId!!]
                        if (boxModel == null) {
                            viewModel.getProfile(
                                viewModel.currentBoxId!!,
                                viewModel.boxHistoryType,
                                viewModel.boxHistoryNumber
                            )
                        } else {
                            setCurrentBoxData(boxModel)
                        }
                        super.onPageSelected(position)
                    }
                }
            }
        )

    }

    private fun setCurrentBoxData(boxModel: BoxHistoryRepoModel) {
        val mainChartPoints = mutableListOf<Point>()
        val mainChartData = boxModel.mainChart.data
        viewModel.xListChart.clear()

        mainChartData.forEachIndexed{ index , value ->
            val date = convertToPersianDate(value.date)
            viewModel.xListChart.add("${date.persianMonth} / ${date.persianDay}")
            mainChartPoints.add(
                Point(
                    index.toFloat(),
                    value.price.toFloat(),
                    boxModel.percent,
                    value.price.toLong()
                )
            )
        }
        if (mainChartPoints.size > 0)
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
                touchEnabled = true,
                xList = viewModel.xListChart
            )


        var totalQuantity: Long = 0
        boxModel.circleChart.forEach { totalQuantity += it.quantity }
        val pieChartDataList = boxModel.circleChart.map {
            AppropriateInvestmentFragment.PieChartData(
                (100 * it.quantity / totalQuantity).toFloat(),
                it.name,
                it.color
            )
        }
        val fragment: AppropriateInvestmentFragment =
            childFragmentManager.findFragmentById(R.id.appropriate_investment_fragment)
                    as AppropriateInvestmentFragment
        fragment.pieChartDataList.clear()
        fragment.pieChartDataList.addAll(pieChartDataList)
        fragment.setup()
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