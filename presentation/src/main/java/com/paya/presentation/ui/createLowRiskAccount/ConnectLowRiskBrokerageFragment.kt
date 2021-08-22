package com.paya.presentation.ui.createLowRiskAccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
    private val mViewModel: ConnectLowRiskBrokerageViewModel by viewModels()
    private val args: ConnectLowRiskBrokerageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_connect_low_risk_brokerage, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        toolbar.backClick = {
            findNavController().popBackStack()
        }
        addInvestBtn.setOnClickListener {
            if (mViewModel.tabCheckedIsSoodinow)
                return@setOnClickListener
            findNavController().navigate(
                ConnectLowRiskBrokerageFragmentDirections.navigateToCreateAccountRulesFragment(
                    args.SelectedPrice,
                    args.riskState
                )
            )
        }
        accountCardLayout.wealthValue.text = Utils.separatorAmount(args.SelectedPrice)



		val adapter = CreateLowRiskAccountFragmentAdapter(requireContext(), requireActivity().supportFragmentManager, tabLayout.tabCount)
		lowRiskBrokerageViewPager.adapter = adapter

		lowRiskBrokerageViewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

       /* txtDescBrokerage.setOnClickListener {
            openUrl("https://reg.irfarabi.com/reg/?j=1&is=1&ref=10112")
        }
        context?.let { context ->

            txtDescBrokerage.setArrayStringText(
                context.resources.getStringArray(R.array.brokerage_desc_bottom),
                ContextCompat.getColor(context, R.color.green)
            )
            txtDescBrokerageSoodinow.setArrayStringText(
                context.resources.getStringArray(R.array.brokerage_desc_bottom_soodinow),
                ContextCompat.getColor(context, R.color.green)
            )
        }*/
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {

				mViewModel.tabCheckedIsSoodinow = tabLayout.selectedTabPosition == 0
                //todo addInvestBtn.isEnabled = !mViewModel.tabCheckedIsSoodinow
                addInvestBtn.setBackgroundResource(if (mViewModel.tabCheckedIsSoodinow) R.drawable.bg_button_gray else R.drawable.bg_button_green)
                /*image.setImageResource(if (mViewModel.tabCheckedIsSoodinow) R.drawable.ic_logo_soodinow else R.drawable.ic_farabi)
                txtDescBrokerage.visibility =
                    if (mViewModel.tabCheckedIsSoodinow) View.GONE else View.VISIBLE
                txtDescBrokerageSoodinow.visibility =
                    if (mViewModel.tabCheckedIsSoodinow) View.VISIBLE else View.GONE

                context?.let { context ->
                    txtDesc.setArrayStringText(
                        context.resources.getStringArray(
                            if (mViewModel.tabCheckedIsSoodinow)
                                R.array.brokerage_desc_soodinow
                            else
                                R.array.brokerage_desc
                        ),
                        ContextCompat.getColor(context, R.color.green)
                    )

                }*/
				//
				lowRiskBrokerageViewPager.currentItem = tabLayout.selectedTabPosition

            }

        })
        tabLayout.getTabAt(1)?.select()

    }

    override fun onDestroyView() {

        super.onDestroyView()
    }

    override val baseViewModel: BaseViewModel
        get() = mViewModel
}