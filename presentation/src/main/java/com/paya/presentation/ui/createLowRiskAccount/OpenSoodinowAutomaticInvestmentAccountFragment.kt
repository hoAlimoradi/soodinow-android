package com.paya.presentation.ui.createLowRiskAccount

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.paya.domain.models.repo.ChartProfitRepoModel
import com.paya.domain.models.repo.PreInvoiceRepoModel
import com.paya.domain.models.repo.WalletChargeRepoModel
import com.paya.domain.models.repo.WalletHostDetailRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentWalletSoodinowOpenAutomaticInvestmentAccountBinding
import com.paya.presentation.ui.adapter.chartLablel.ChartLabelAdapter
import com.paya.presentation.ui.model.PieChartModel
import com.paya.presentation.ui.profile.dialog.ChartProfileDialog
import com.paya.presentation.utils.*
import com.paya.presentation.viewmodel.OpenSoodinowAutomaticViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_wallet_soodinow_open_automatic_investment_account.*

const val HOST_ID = "host_id"

@AndroidEntryPoint
class OpenSoodinowAutomaticInvestmentAccountFragment :
    BaseFragment<OpenSoodinowAutomaticViewModel>() {

    var boxId: Long = 0
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private var binding: FragmentWalletSoodinowOpenAutomaticInvestmentAccountBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mViewModel.hostId = it.getInt(HOST_ID, -1)
        }
    }

    private val mViewModel: OpenSoodinowAutomaticViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWalletSoodinowOpenAutomaticInvestmentAccountBinding.inflate(
            inflater,
            container,
            false
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val watcher = NumberTextWatcher(
            priceInvest,
            ",###",
            lifecycleScope
        ) { }

        priceInvest.addTextChangedListener(watcher)
        changeStatusBarColorToLightPurple()

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    backPressed()
                }
            })
        setupPieChart()
        initChartLabelRecyclerView()
        backButtonOpenSoodinowAutomaticInvestmentAccountFragment.setOnClickListener {
            backPressed()
        }

        observe(mViewModel.hostLiveData, ::onReady)
        observe(mViewModel.preInvoiceLiveData, ::onReadyPreInvoice)
        observe(mViewModel.walletChargeLiveData, ::chargeReady)
        riskInvestmentCustomSeekbar.currentValue = 30f

        navigateToDepositSoodinowWalletFragmentButton.setOnClickListener {

            val price: Long = Utils.getAmount(priceInvest.text.toString()) ?: 0
            if (price <= 0) {
                Toast.makeText(
                    requireContext(), getString(R.string.price_error), Toast.LENGTH_SHORT
                ).show()

            } else {
                mViewModel.preInvoice(price)

            }


        }
        efficiencyButton.setOnClickListener {

            //openChart()
        }

        AddInventoryTitle.setOnClickListener {
            val modalbottomSheetFragment = AddInventoryBottomSheetDialogFragment()
            modalbottomSheetFragment.onClick = {
                mViewModel.charge(it)
            }
            modalbottomSheetFragment.show(parentFragmentManager, modalbottomSheetFragment.tag)

        }

    }

    private fun chargeReady(resource: Resource<WalletChargeRepoModel>) {
        if (resource.status == Status.SUCCESS) {
            resource.data?.let {
                openUrl(it.link)
            }
        }
    }

    private fun onReady(resource: Resource<WalletHostDetailRepoModel>) {
        if (resource.status == Status.SUCCESS) {
            resource.data?.let {
                binding?.apply {
                    monthlyPercentValue.text = it.efficiency.month.percent.toString() + "%"
                    trimesterPercentValue.text = it.efficiency.threeMonth.percent.toString() + "%"
                    weeklyPercentValue.text = it.efficiency.week.percent.toString() + "%"
                    var pieChartModel = PieChartModel()
                    pieChartModel?.let { pieChartModel ->
                        if (it.basketDetail.isNotEmpty() && pieChartModel.chartLabels.isEmpty() && pieChartModel.entries.isEmpty()) {
                            var allSize: Long = 0
                            it.basketDetail.forEach {
                                allSize += (it.percent * 100).toLong()
                            }
                            it.basketDetail.forEachIndexed { _, pieChartData ->
                                if (pieChartModel.entries.size <= it.basketDetail.size && allSize > 0)
                                    pieChartModel.entries.add(PieEntry(((pieChartData.percent * 100).toLong()).toFloat()))
                                if (pieChartModel.chartColor.size <= it.basketDetail.size)
                                    pieChartModel.chartColor.add(Color.parseColor(pieChartData.color))
                                if (pieChartModel.chartLabels.size <= it.basketDetail.size)
                                    pieChartModel.chartLabels.add(
                                        ChartLabelAdapter.ChartLabelModel(
                                            pieChartData.namad,
                                            pieChartData.color
                                        )
                                    )
                            }
                            setData(pieChartModel)
                        }
                    }

                }
            }
        }
    }

    private fun onReadyPreInvoice(resource: Resource<PreInvoiceRepoModel>) {
        if (resource.status == Status.SUCCESS) {
            getFindViewController()?.navigateUp()
            getFindViewController()?.navigate(
                R.id.depositSoodinowWalletFragment
            )
        }
    }

    private fun readyProfile(resource: Resource<List<ChartProfitRepoModel>>) {
        if (resource.status == Status.SUCCESS) {
            resource.data?.let {
                if (it.isNotEmpty()) {
                    /* initTab(it)
                     setCurrentBoxData(it.size - 1)*/
                    val dialog = ChartProfileDialog()
                    dialog.show(parentFragmentManager, "chartDialog")
                }
            }

        }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.hostDetail()
    }


    private fun backPressed() {
        activity?.let {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                it.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
            it.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            it.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            it.window.statusBarColor = ContextCompat.getColor(it.baseContext, R.color.white)
        }

        findNavController().popBackStack()
    }

    private fun changeStatusBarColorToLightPurple() {
        activity?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                it.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE
            }
            it.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            it.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            it.window.statusBarColor =
                ContextCompat.getColor(it.baseContext, R.color.light_purple6112D8)
        }
    }

    private fun setData(pieChartModel: PieChartModel) {
        pieChart?.apply {
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
            pieChart?.apply {
                this.data = data
                // undo all highlights
                highlightValues(null)
                notifyDataSetChanged()
            }
        }
        setupChartLabelRecyclerView(pieChartModel)

    }

    private fun initChartLabelRecyclerView() {
        chartLabelRecyclerView?.apply {
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
            pieChart?.apply {
                val y = data.dataSets[0].getEntryForIndex(it).y
                highlightValue(it.toFloat(), y, 0)
            }
        }

        chartLabelRecyclerView?.apply { adapter = adapterChart }

    }

    private fun setupPieChart() {
        pieChart?.apply {
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

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override val baseViewModel: BaseViewModel
        get() = mViewModel
}



