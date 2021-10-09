package com.paya.presentation.ui.createLowRiskAccount

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.paya.domain.models.repo.ChartProfitRepoModel
import com.paya.domain.models.repo.SoodinowWalletContractRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.ui.profile.dialog.ChartProfileDialog
import com.paya.presentation.utils.hideKeyBoard
import com.paya.presentation.utils.observe
import com.paya.presentation.utils.requestKeyBoard
import com.paya.presentation.viewmodel.CreateLowRiskAccountViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_wallet_soodinow_open_automatic_investment_account.*
import kotlinx.android.synthetic.main.layout_add_inventory_modal_bottom_sheet.*
import kotlinx.android.synthetic.main.open_automatic_investment_account_card.*


@AndroidEntryPoint
class OpenSoodinowAutomaticInvestmentAccountFragment : BaseFragment<CreateLowRiskAccountViewModel>() {

    var boxId: Long = 0
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private val mViewModel: CreateLowRiskAccountViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wallet_soodinow_open_automatic_investment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeStatusBarColorToLightPurple()

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                backPressed()
            }
        })

        backButtonOpenSoodinowAutomaticInvestmentAccountFragment.setOnClickListener {
            backPressed()
        }
        setupWalletInputPrice()
        observe(mViewModel.soodinowWalletRepoModelResource, ::onReady)
        observe(mViewModel.chartProfit, ::readyProfile)
        riskInvestmentCustomSeekbar.currentValue = 30f

        navigateToDepositSoodinowWalletFragmentButton.setOnClickListener {

            val price: Long = walletInputPrice.getPriceLong()?.let { it } ?: 0
            if (price <= 0) {
                Toast.makeText(
                    requireContext(), getString(R.string.price_error), Toast.LENGTH_SHORT
                ).show()

            } else {
                getFindViewController()?.navigate(
                    R.id.depositSoodinowWalletFragment
                )
            }


        }
        efficiencyButton.setOnClickListener {
            val modalbottomSheetFragment = AddInventoryBottomSheetDialogFragment()
            modalbottomSheetFragment.show(parentFragmentManager,modalbottomSheetFragment.tag)
            //openChart()
        }


       /* bottomSheetBehavior = BottomSheetBehavior.from(addInventoryBottomSheetDialogFragmentConstraintLayout)

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // handle onSlide
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> Toast.makeText(requireContext(), "STATE_COLLAPSED", Toast.LENGTH_SHORT).show()
                    BottomSheetBehavior.STATE_EXPANDED -> Toast.makeText(requireContext(), "STATE_EXPANDED", Toast.LENGTH_SHORT).show()
                    BottomSheetBehavior.STATE_DRAGGING -> Toast.makeText(requireContext(), "STATE_DRAGGING", Toast.LENGTH_SHORT).show()
                    BottomSheetBehavior.STATE_SETTLING -> Toast.makeText(requireContext(), "STATE_SETTLING", Toast.LENGTH_SHORT).show()
                    BottomSheetBehavior.STATE_HIDDEN -> Toast.makeText(requireContext(), "STATE_HIDDEN", Toast.LENGTH_SHORT).show()
                    else -> Toast.makeText(requireContext(), "OTHER_STATE", Toast.LENGTH_SHORT).show()
                }
            }
        })*/

        walletInputPrice.setOnClickListener {
            AddInventoryBottomSheetDialogFragment().apply {
                show(parentFragmentManager, AddInventoryBottomSheetDialogFragment.TAG)
            }
        }

        /*btnBottomSheetPersistent.setOnClickListener {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            else
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }*/
    }

    private fun setupWalletInputPrice() {
        walletInputPrice?.apply {
            setupWatcherPrice(lifecycleScope) {
                //TODO api call
            }
        }
    }
    private fun onReady(resource: Resource<List<SoodinowWalletContractRepoModel>>) {

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
        mViewModel.getSoodinowWalletContracts()
    }

    private fun openChart() {
        mViewModel.getChartProfit(boxId)
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
            it.window.statusBarColor = ContextCompat.getColor(it.baseContext, R.color.light_purple6112D8)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override val baseViewModel: BaseViewModel
        get() = mViewModel
}



