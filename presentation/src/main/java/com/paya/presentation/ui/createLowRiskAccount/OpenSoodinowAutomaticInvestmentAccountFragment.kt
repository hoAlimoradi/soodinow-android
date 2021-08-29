package com.paya.presentation.ui.createLowRiskAccount

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.paya.domain.models.repo.SoodinowWalletContractRepoModel
import com.paya.domain.tools.Resource
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
import kotlinx.android.synthetic.main.open_automatic_investment_account_card.*


@AndroidEntryPoint
class OpenSoodinowAutomaticInvestmentAccountFragment : BaseFragment<CreateLowRiskAccountViewModel>() {
    val unitValue = 500
    var currentWealthValue: Int = 0
    private val mViewModel: CreateLowRiskAccountViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wallet_soodinow_open_automatic_investment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeStatusBarColorToGreen()

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                backPressed()
            }
        })

        backButtonOpenSoodinowAutomaticInvestmentAccountFragment.setOnClickListener {
            backPressed()
        }

        observe(mViewModel.soodinowWalletRepoModelResource, ::onReady)
        riskInvestmentCustomSeekbar.currentValue = 30f

        navigateToDepositSoodinowWalletFragmentButton.setOnClickListener {
            getFindViewController()?.navigate(
                R.id.depositSoodinowWalletFragment
            )
        }

        wealthValueEditText.requestKeyBoard()
        wealthValueEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                if(wealthValueEditText.text.toString().isNotBlank() && wealthValueEditText.text.toString().isNotEmpty()) {
                    currentWealthValue = wealthValueEditText.text.toString().toInt()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })

        plusPriceImage.setOnClickListener {
            currentWealthValue += unitValue
            wealthValueEditText.text = Editable.Factory.getInstance().newEditable(currentWealthValue.toString())
        }
        efficiencyButton.setOnClickListener {
            openChart()
        }
        minusPriceImage.setOnClickListener {
            if (wealthValueEditText.text.toString().toInt()> unitValue ) {
                currentWealthValue  -= unitValue
                wealthValueEditText.text = Editable.Factory.getInstance().newEditable(currentWealthValue.toString())
            }

        }
    }

    private fun onReady(resource: Resource<List<SoodinowWalletContractRepoModel>>) {

    }

    override fun onResume() {
        super.onResume()
        mViewModel.getSoodinowWalletContracts()
    }

    private fun openChart() {
        val dialog = ChartProfileDialog()
        dialog.show(parentFragmentManager, "chartDialog")
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

    private fun changeStatusBarColorToGreen() {
        activity?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                it.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE
            }
            it.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            it.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            it.window.statusBarColor = ContextCompat.getColor(it.baseContext, R.color.green)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override val baseViewModel: BaseViewModel
        get() = mViewModel
}