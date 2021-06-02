package com.paya.presentation.ui.profile.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.paya.domain.models.repo.BoxHistoryRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseDialogFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.DialogChartProfileBinding
import com.paya.presentation.ui.profile.enum.FilterProfile
import com.paya.presentation.utils.BindingAdapters
import com.paya.presentation.utils.observe
import com.paya.presentation.utils.setWidthPercent
import com.paya.presentation.viewmodel.ChartProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_chart_profile.*

@AndroidEntryPoint
class ChartProfileDialog : BaseDialogFragment<ChartProfileViewModel>() {
    private val viewModel: ChartProfileViewModel by viewModels()
    private lateinit var mBinding: DialogChartProfileBinding
    var boxId: Long = 0
        get() = field
        set(value) {
            field = value
        }
    override val baseViewModel: BaseViewModel
        get() = viewModel

    override fun onStart() {
        super.onStart()
        setWidthPercent(90)
        dialog?.window?.let {
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_chart_profile,
            container,
            false
        )

        mBinding.lifecycleOwner = this
        mBinding.viewModel = viewModel

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.profile, ::readyProfile)
        initTab()
        viewModel.getProfile(boxId)
        mBinding.closeBtn.setOnClickListener {
            dismissAllowingStateLoss()
        }
    }

    private fun readyProfile(resource: Resource<BoxHistoryRepoModel>) {
        if (resource.status == Status.SUCCESS) {
            setCurrentBoxData()
        }
    }

    private fun setCurrentBoxData() {
        if (viewModel.mainChartPoints.size > 0)
            BindingAdapters.setLineAccountChartData(
                chart,
                viewModel.mainChartPoints,
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

    }

    private fun initTab() {
        chartTabLayout.getTabAt(2)?.select()
        chartTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            // TODO: 4/2/21 box history Number what?
            override fun onTabSelected(tab: TabLayout.Tab?) {

                when (chartTabLayout.selectedTabPosition) {
                    // day
                    2 -> {
                        viewModel.filterProfile = FilterProfile.day
                        viewModel.number = 3
                    }
                    //week
                    1 -> {
                        viewModel.filterProfile = FilterProfile.week
                        viewModel.number = 1
                    }
                    //month
                    0 -> {
                        viewModel.filterProfile = FilterProfile.month
                        viewModel.number = 1
                    }
                    //years
                    // TODO: 4/26/21 change to years
                    -1 -> {
                        viewModel.filterProfile = FilterProfile.month
                        viewModel.number = 3
                    }
                    else -> {
                        viewModel.filterProfile = FilterProfile.day
                        viewModel.number = 3
                    }
                }
                viewModel.getProfile(boxId)
            }

        })

    }
}