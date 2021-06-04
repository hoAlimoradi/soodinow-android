package com.paya.presentation.ui.cashManager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.paya.domain.models.repo.ExitAccountRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentCashManagerBinding
import com.paya.presentation.ui.hint.fragments.CardAccount
import com.paya.presentation.utils.Utils
import com.paya.presentation.utils.ViewPagerUtil
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.CashManagerViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_ACCOUNT_ID = "account_id"
@AndroidEntryPoint
class CashManagerFragment : BaseFragment<CashManagerViewModel>() {

    private val mViewModel: CashManagerViewModel by viewModels()
    private  var mBinding: FragmentCashManagerBinding?=null
    private val cardAccounts = mutableListOf<CardAccount>()
    private var accountId: Long = 0
    private  var adapterSlide: SlidePagerAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            accountId = it.getLong(ARG_ACCOUNT_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_cash_manager, container, false)
        mBinding?.apply {
            lifecycleOwner = this@CashManagerFragment
            viewModel = mViewModel
        }
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()

        observe(mViewModel.existAccount, ::onExistAccountReady)
        observe(mViewModel.sellPriceStatus, ::readySellPrice)
        observe(mViewModel.pullPriceStatus, ::readyPullPrice)
        mBinding?.apply {
            toolbar.backButton.setOnClickListener {
                findNavController().popBackStack()
            }
            inputPrice.setupWatcherPrice(lifecycleScope = lifecycleScope) {}
            managerAccountHistory.setOnClickListener {
                findNavController().navigate(
                    R.id.financialReportFragment
                )
            }


            submitBtn.setOnClickListener {
                mViewModel.price.set(inputPrice.getPriceLong())
                mViewModel.setPullPrice()
            }

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tabLayout.selectedTabPosition) {
                        0 -> {
                            mViewModel.priceType.set(CashManagerViewModel.PriceType.withdrawal)
                            inputPrice.maxPrice = mViewModel.maxSeek.get()!!
                            inputPrice.minPrice = mViewModel.minSeek.get()!!
                            inputPrice.setMessage(
                                "مبلغ وارد شده باید بین ${Utils.separatorAmount(
                                    mViewModel.minSeek.get()!!
                                )} تا ${Utils.separatorAmount(mViewModel.maxSeek.get()!!)} ریال باشد"
                            )
                            mViewModel.type.set("Fixed")
                        }
                        1 -> {
                            mViewModel.priceType.set(CashManagerViewModel.PriceType.deposit)
                            inputPrice.maxPrice = 0L
                            inputPrice.minPrice = 0L
                            inputPrice.setMessage("")
                            mViewModel.type.set("no_risk")
                        }
                    }
                    percentGroup.visibility =
                        if (mViewModel.priceType.get() == CashManagerViewModel.PriceType.deposit) View.GONE else View.VISIBLE
                }

            })
            changeTabColor()
            tabLayout.getTabAt(1)?.select()
        }

    }


    private fun changeTabColor() {
        mBinding?.apply {
            val tempLinearLayout1: LinearLayout =
                (tabLayout.getChildAt(0) as LinearLayout).getChildAt(0) as LinearLayout
            tempLinearLayout1.setBackgroundResource(R.drawable.selector_tab_brokerage_red)
            val tempLinearLayout2: LinearLayout =
                (tabLayout.getChildAt(0) as LinearLayout).getChildAt(1) as LinearLayout
            tempLinearLayout2.setBackgroundResource(R.drawable.selector_tab_brokerage)
        }
    }


    private fun readySellPrice(resource: Resource<List<Long>>) {
        when (resource.status) {
            Status.SUCCESS -> {
                resource.data?.let { }
            }

            else -> return
        }
    }

    private fun readyPullPrice(resource: Resource<String>) {
        when (resource.status) {
            Status.SUCCESS -> {
                Toast.makeText(
                    context,
                    getString(R.string.pull_price_message_insert),
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> return
        }
    }

    private fun setupViewPager() {
        adapterSlide = SlidePagerAdapter(this)
        mBinding?.pager?.apply {
            offscreenPageLimit = 1
            adapter = adapterSlide
            setPadding(
                resources.getDimension(R.dimen.viewpager_item_padding).toInt(),
                0,
                resources.getDimension(R.dimen.viewpager_item_padding).toInt(), 0
            )
            setPageTransformer(ViewPagerUtil.getTransformer(requireContext().resources))
            addItemDecoration(ViewPagerUtil.getItemDecoration(requireContext()))

            registerOnPageChangeCallback(
                object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        cardAccounts[position].activeBoxRepo?.let {
                            mViewModel.getSellPrice(it.type!!)
                            mViewModel.type.set(it.subType)
                            mBinding?.apply{inputPrice.setPrice(it.price.toString())}
                        }
                    }
                }
            )
        }
    }

    private fun onExistAccountReady(resource: Resource<ExitAccountRepoModel>) {
        if (resource.status == Status.SUCCESS) {
            cardAccounts.clear()
            resource.data?.activeBox?.let {
                var selectionPager = 0
                it.forEach { activeBox ->
                    cardAccounts.add(CardAccount.newInstance(activeBox, false))
                    if (accountId == activeBox.id) {
                        selectionPager = cardAccounts.size - 1
//                        mViewModel.getSellPrice(activeBox.type)
                        mViewModel.type.set(activeBox.subType)
                        mBinding?.apply{inputPrice.setPrice(activeBox.price.toString())}
                    }
                }
                mBinding?.pager?.apply{currentItem = selectionPager}
                if (it.isEmpty()) {
                    return@let
                }
                adapterSlide?.apply{notifyDataSetChanged()}
                // TODO: 4/11/21 add type and price
            }
        }
    }


    override val baseViewModel: BaseViewModel
        get() = mViewModel


    private inner class SlidePagerAdapter(f: Fragment) : FragmentStateAdapter(f) {
        override fun getItemCount(): Int = cardAccounts.size

        override fun createFragment(position: Int): Fragment = cardAccounts[position]
    }

    override fun onDestroyView() {
        mBinding?.pager?.apply { adapter = null }
        mBinding = null
        super.onDestroyView()
    }
    companion object {
        @JvmStatic
        fun newBundle(accountId: Long) =
            Bundle().apply {
                putSerializable(ARG_ACCOUNT_ID, accountId)
            }
    }

}