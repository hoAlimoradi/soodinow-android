package com.paya.presentation.ui.createLowRiskAccount

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayout
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentConnectLowRiskBrokerageBinding
import com.paya.presentation.ui.createLowRiskAccount.adapter.CreateLowRiskAccountFragmentAdapter
import com.paya.presentation.utils.Utils
import com.paya.presentation.utils.openUrl
import com.paya.presentation.utils.setArrayStringText
import com.paya.presentation.viewmodel.ConnectLowRiskBrokerageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.brokerage_account_card.view.*
import kotlinx.android.synthetic.main.fragment_connect_low_risk_brokerage.*

@AndroidEntryPoint
class ConnectLowRiskBrokerageFragment : BaseFragment<ConnectLowRiskBrokerageViewModel>() {
    private lateinit var adapter : CreateLowRiskAccountFragmentAdapter
    private lateinit var fm: FragmentManager
    private val mViewModel: ConnectLowRiskBrokerageViewModel by viewModels()
    //private val args: ConnectLowRiskBrokerageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_connect_low_risk_brokerage, container, false)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //fm = context. supportFragmentManager
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.backClick = {
            findNavController().popBackStack()
        }
        addInvestBtn.setOnClickListener {
            if (mViewModel.tabCheckedIsSoodinow)
                getFindViewController()?.navigate(
                    R.id.openSoodinowAutomaticInvestmentAccountFragment
                )

            else {
                getFindViewController()?.navigate(
                    R.id.createLowRiskAccount
                )
            }
        }
        accountCardLayout.wealthValue.text = "10.856.000"
        adapter = CreateLowRiskAccountFragmentAdapter(requireContext(),childFragmentManager, tabLayout.tabCount)
        lowRiskBrokerageViewPager.adapter = adapter
        lowRiskBrokerageViewPager.offscreenPageLimit = 2
        lowRiskBrokerageViewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {

                mViewModel.tabCheckedIsSoodinow = tabLayout.selectedTabPosition == 0
                //todo addInvestBtn.isEnabled = !mViewModel.tabCheckedIsSoodinow
                addInvestBtn.setBackgroundResource(if (mViewModel.tabCheckedIsSoodinow) R.drawable.bg_button_gray else R.drawable.bg_button_green)
                lowRiskBrokerageViewPager.currentItem = tabLayout.selectedTabPosition

            }

        })
        tabLayout.getTabAt(1)?.select()
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onDestroy() {
        super.onDestroy()
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDetach() {
        super.onDetach()
    }

    override val baseViewModel: BaseViewModel
        get() = mViewModel
}