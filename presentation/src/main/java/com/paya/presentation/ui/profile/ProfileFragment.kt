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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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
import com.paya.presentation.utils.BindingAdapters
import com.paya.presentation.utils.Utils
import com.paya.presentation.utils.ViewPagerUtil
import com.paya.presentation.utils.observe
import com.paya.presentation.utils.shared.Point
import com.paya.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileViewModel>() {

    private lateinit var mBinding: FragmentProfileBinding
    private lateinit var adapter: SlidePagerAdapter
    private val viewModel: ProfileViewModel by viewModels()

    private val boxHistoryHahMap = mutableMapOf<Long, BoxHistoryRepoModel?>()
    private var currentBoxId: Long? = null
    private var boxHistoryId: List<Long>? = null
    private val cardAccounts = mutableListOf<CardAccount>()
    private var boxHistoryType = "day"
    private var boxHistoryNumber = 3

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
        viewModel.getExistAccount()
        initRadioGroup()
        observe(viewModel.existAccount, ::onExistAccountReady)
        observe(viewModel.profile, ::onProfileReady)


    }

    private fun initRadioGroup() {
        mBinding.chartTabLayout.getTabAt(3)?.select()
        mBinding.chartTabLayout.addOnTabSelectedListener( object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            // TODO: 4/2/21 box history Number what?
            override fun onTabSelected(tab: TabLayout.Tab?) {
                currentBoxId ?: return
                when (mBinding.chartTabLayout.selectedTabPosition) {
                    // day
                   3 -> {
                        boxHistoryType = "day"
                        boxHistoryNumber = 3
                    }
                    //week
                    2 -> {
                        boxHistoryType = "week"
                        boxHistoryNumber = 1
                    }
                    //month
                    1 -> {
                        boxHistoryType = "month"
                        boxHistoryNumber = 1
                    }
                    //years
                    0 -> {
                        boxHistoryType = "month"
                        boxHistoryNumber = 3
                    }
                    else -> {
                        boxHistoryType = "day"
                        boxHistoryNumber = 3
                    }
                }
                currentBoxId?.let { viewModel.getProfile(it, boxHistoryType, boxHistoryNumber) }
            }

        })

    }

    private fun onExistAccountReady(resource: Resource<ExitAccountRepoModel>) {
        if (resource.status == Status.SUCCESS) {
            resource.data?.activeBox?.let {
                boxHistoryId = it.map { activeBox -> activeBox.id }
                it.forEach { activeBox ->
                    boxHistoryHahMap[activeBox.id] = null
                    cardAccounts.add(CardAccount.newInstance(activeBox))
                }
                if (it.isEmpty()) {
                    viewModel.setErrorMessage("شما هیچ حسابی ایجاد نکرده اید")
                    return@let
                }
                currentBoxId = it.first().id
                viewModel.getProfile(it.first().id, boxHistoryType, boxHistoryNumber)
            }
        } else if (resource.status == Status.ERROR) {
            viewModel.setErrorMessage(resource.message ?: "خطایی رخ داده است")
        }
    }

    private fun onProfileReady(resource: Resource<BoxHistoryRepoModel>) {
        if (resource.status == Status.SUCCESS) {
            boxHistoryHahMap[currentBoxId!!] = resource.data
            resource.data?.let {
                val cardAccountIndex = boxHistoryId!!.indexOf(currentBoxId!!)
                if (cardAccountIndex == -1) return
                val cardAccount = cardAccounts[cardAccountIndex]
                if (!cardAccount.isDataSet)
                    adapter.notifyItemChanged(cardAccountIndex)
                mBinding.parentView.visibility = View.VISIBLE
                lifecycleScope.launch {
                    delay(500)
                    setCurrentBoxData(cardAccount, it)
                }
            }
        } else if (resource.status == Status.ERROR) {
            viewModel.setErrorMessage(resource.message ?: "خطایی رخ داده است")
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
                    currentBoxId = boxHistoryId!![position]
                    val boxModel = boxHistoryHahMap[currentBoxId!!]
                    if (boxModel == null) {
                        viewModel.getProfile(currentBoxId!!, boxHistoryType, boxHistoryNumber)
                    } else {
                        setCurrentBoxData(cardAccounts[position], boxModel)
                    }
                    super.onPageSelected(position)
                }
            }
        )

    }

    private fun setCurrentBoxData(cardAccount: CardAccount, boxModel: BoxHistoryRepoModel) {
        val mainChartPoints = mutableListOf<Point>()
        val mainChartData = boxModel.mainChart.data
        mainChartData.forEachIndexed { index, value ->
            mainChartPoints.add(
                Point(
                    index.toFloat(),
                    value.toFloat(),
                    boxModel.percent,
                    value
                )
            )
        }
        BindingAdapters.setLineAccountChartData(
            mBinding.chart,
            mainChartPoints,
            chartColor = ContextCompat.getColor(requireContext(), R.color.japanese_laurel_green),
            markerColor = ContextCompat.getColor(requireContext(), R.color.conifer_green),
            markerTitleColor = Color.WHITE,
            chartAlpha = 0,
            markerType = 0,
            touchEnabled = true
        )


        var totalQuantity: Long = 0
        boxModel.circleChart.forEach { totalQuantity += it.quantity }
        val pieChartDataList = boxModel.circleChart.map {
            AppropriateInvestmentFragment.PieChartData(
                (100 * it.quantity / totalQuantity).toFloat(),
                it.name
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
        override fun getItemCount(): Int = cardAccounts.size

        override fun createFragment(position: Int): Fragment = cardAccounts[position]
    }


    override val baseViewModel: BaseViewModel
        get() = viewModel

}