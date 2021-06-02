package com.paya.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.paya.domain.models.repo.CurrencyPriceRepoModel
import com.paya.domain.models.repo.ProfileRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentHomeBinding
import com.paya.presentation.ui.cardAccount.NewCardAccountFragment
import com.paya.presentation.ui.createPersonalAccount.FirstInformationFragment
import com.paya.presentation.ui.home.adapter.MarketAdapter
import com.paya.presentation.ui.publicDialog.NotificationEmptyDialog
import com.paya.presentation.utils.ViewPagerUtil
import com.paya.presentation.utils.observe
import com.paya.presentation.utils.setAllOnClickListener
import com.paya.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_new_card_account.*

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel>() {
	
	private lateinit var adapter: SlidePagerAdapter
	private lateinit var adapterCurrency: MarketAdapter
	private lateinit var mBinding: FragmentHomeBinding
	private val mViewModel: HomeViewModel by viewModels()
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = DataBindingUtil.inflate(
			inflater,
			R.layout.fragment_home,
			container,
			false
		)
		mBinding.viewModel = mViewModel
		mBinding.lifecycleOwner = this
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		observe(mViewModel.currencyPrice, ::onPricesReady)
		observe(mViewModel.statusProfile, ::checkProfile)
		val manager = LinearLayoutManager(context)
		adapterCurrency = MarketAdapter()
		mBinding.marketRecycleView.adapter = adapterCurrency
		mBinding.marketRecycleView.layoutManager = manager
		mViewModel.getCurrencyPrices()
		mBinding.whyGroup.setAllOnClickListener {
			getFindViewController()?.navigate(R.id.whySoodinow)
		}

		setupViewPager()

		mBinding.alarm.setOnClickListener {
			NotificationEmptyDialog().show(parentFragmentManager, "notification dialog")
		}


	}

	
	private fun setupViewPager() {
		adapter = SlidePagerAdapter(this) {
			mViewModel.getProfile()
		}
		mBinding.pager.offscreenPageLimit = 1
		mBinding.pager.adapter = adapter
		mBinding.pager.setPadding(
			resources.getDimension(R.dimen.viewpager_item_padding).toInt(),
			0,
			resources.getDimension(R.dimen.viewpager_item_padding).toInt(), 0
		)
		mBinding.pager.setPageTransformer(ViewPagerUtil.getTransformer(requireContext().resources))
		mBinding.pager.addItemDecoration(ViewPagerUtil.getItemDecoration(requireContext()))
		mBinding.tabAccountCard.setViewPager2(mBinding.pager)
	}
	
	private fun onPricesReady(resource: Resource<List<CurrencyPriceRepoModel>>){
		when(resource.status){
			Status.SUCCESS -> resource.data?.let {
				adapterCurrency.clear()
				adapterCurrency.addAllData(it as ArrayList<CurrencyPriceRepoModel>)
			}


			else -> return
		}
	}

	private inner class SlidePagerAdapter(f: Fragment, val onItemClick: (position: Int) -> Unit) :
		FragmentStateAdapter(f) {
		override fun getItemCount(): Int = 1

		override fun createFragment(position: Int): Fragment {
			val fragment =  NewCardAccountFragment()
			fragment.setClickListener(onItemClick,position)
			return fragment
		}
	}

	private fun checkProfile(resource: Resource<ProfileRepoModel>) {
		if (resource.status == Status.SUCCESS) {
			if (!resource.data!!.complete) {
				getFindViewController()?.navigate(
					R.id.firstInformation, FirstInformationFragment.newBundle(true)
				)
			} else {
				getFindViewController()?.navigate(
					R.id.createLowRiskAccount
				)
			}
		}
	}

	override val baseViewModel: BaseViewModel
		get() = mViewModel


}