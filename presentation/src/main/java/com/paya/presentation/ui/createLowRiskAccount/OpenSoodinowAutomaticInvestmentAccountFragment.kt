package com.paya.presentation.ui.createLowRiskAccount

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
import com.paya.presentation.ui.profile.dialog.ChartProfileDialog
import com.paya.presentation.utils.NumberTextWatcher
import com.paya.presentation.utils.Utils
import com.paya.presentation.utils.mosaiclayout.CellView
import com.paya.presentation.utils.observe
import com.paya.presentation.utils.openUrl
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
                    mosaicLayout.setPercentList(it.basketDetail.map { basket->
                        (basket.percent * 100).toInt()
                    } as MutableList<Int>)
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

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override val baseViewModel: BaseViewModel
        get() = mViewModel
}



