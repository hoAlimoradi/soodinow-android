package com.paya.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.paya.domain.models.repo.CurrencyPriceRepoModel
import com.paya.domain.models.repo.ProfileRepoModel
import com.paya.domain.models.repo.SoodinowWalletValueRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentHomeBinding
import com.paya.presentation.ui.cardAccount.NewCardAccountFragment
import com.paya.presentation.ui.createLowRiskAccount.CreateLowRiskAccountFragmentDirections
import com.paya.presentation.ui.createPersonalAccount.FirstInformationFragment
import com.paya.presentation.ui.home.adapter.MarketAdapter
import com.paya.presentation.ui.publicDialog.NotificationEmptyDialog
import com.paya.presentation.utils.ViewPagerUtil
import com.paya.presentation.utils.observe
import com.paya.presentation.utils.toPersianSeparatedValue
import com.paya.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel>() {

	private var marketRecycleViewIsExpanded: Boolean  = false
	private var adapter: SlidePagerAdapter? = null
	private var adapterCurrency: MarketAdapter? = null
	private var mBinding: FragmentHomeBinding? = null
	private val mViewModel: HomeViewModel by viewModels()
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = FragmentHomeBinding.inflate(
			inflater,
			container,
			false
		)
		return mBinding?.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		observe(mViewModel.currencyPrice, ::onPricesReady)
		observe(mViewModel.statusProfile, ::checkProfile)
		observe(mViewModel.soodinowWalletValueRepoModelResourceMutableLiveData, ::walletValue)
		val layoutManager =
			LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


		/*val layoutManager = GridLayoutManager(
			requireContext(),
			3,
			LinearLayoutManager.VERTICAL,
			false
		)*/

		adapterCurrency = MarketAdapter()
		adapterCurrency?.let {
			mBinding?.apply {
				marketRecycleView.adapter = it
			}
		}
		mBinding?.apply {
			marketRecycleView.layoutManager = layoutManager

			walletImageViewConstraintLayout.setOnClickListener {
				getFindViewController()?.navigate(R.id.wallet)
			}
			whySoodinowConstraintLayout.setOnClickListener {
				getFindViewController()?.navigate(R.id.whySoodinow)
			}
			alarm.setOnClickListener {
				NotificationEmptyDialog().show(parentFragmentManager, "notification dialog")
			}

			/*val params: ViewGroup.LayoutParams = marketRecycleView.layoutParams


			expandAllRowOfMarketRecycleImageView.setOnClickListener {
				marketRecycleViewIsExpanded = !marketRecycleViewIsExpanded
				if (marketRecycleViewIsExpanded) {
					expandAllRowOfMarketRecycleImageView.setImageDrawable(it.context.resources.getDrawable(R.drawable.ic_arrow_left))
					params.height = ViewGroup.LayoutParams.MATCH_PARENT
					marketRecycleView.layoutParams = params
				} else {
					expandAllRowOfMarketRecycleImageView.setImageDrawable(it.context.resources.getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24))
					params.height = 100
					marketRecycleView.layoutParams = params
				}

			}*/
		}
		mViewModel.getCurrencyPrices()
		mViewModel.getSoodinowWalletValue()
		setupViewPager()

	}

	/*private fun showAllRowsOfMarketRecycleImageView() {

	}

	private fun showFirstRowOfMarketRecycleImageView() {

	}*/
	private fun setupViewPager() {
		adapter = SlidePagerAdapter(childFragmentManager,viewLifecycleOwner.lifecycle) {
			getFindViewController()?.navigate(
				//R.id.createLowRiskAccount
				R.id.connectLowRiskBrokerage

			)

			/*findNavController().navigate(
				CreateLowRiskAccountFragmentDirections.navigationToConnectLowRiskBrokerage(
					inputPrice.getPriceLong(),
					("no_risk")
				)
			)*/
		}
		mBinding?.apply {
			pager.offscreenPageLimit = 1
			adapter?.let {
				pager.adapter = it
			}
			pager.setPadding(
				resources.getDimension(R.dimen.viewpager_item_padding).toInt(),
				0,
				resources.getDimension(R.dimen.viewpager_item_padding).toInt(), 0
			)
			pager.setPageTransformer(ViewPagerUtil.getTransformer(requireContext().resources))
			pager.addItemDecoration(ViewPagerUtil.getItemDecoration(requireContext()))
			tabAccountCard.setViewPager2(pager)
		}

	}
	
	private fun onPricesReady(resource: Resource<List<CurrencyPriceRepoModel>>){
		when (resource.status) {
			Status.SUCCESS -> resource.data?.let { markets ->
				adapterCurrency?.let {
					it.clear()
					it.addAllData(markets as ArrayList<CurrencyPriceRepoModel>)
				}
			}


			else -> return
		}
	}

	private fun walletValue(resource: Resource<SoodinowWalletValueRepoModel>) {
		if (resource.status == Status.SUCCESS) {
			val value: Double =  resource.data?.value ?: 0.0
			mBinding?.apply {
				yourWalletValue.text = value.toPersianSeparatedValue()
			}
		}
	}

	private inner class SlidePagerAdapter(val fragmentManager: FragmentManager,
										  val lifecycle: Lifecycle, val onItemClick: (position: Int) -> Unit) :
		FragmentStateAdapter(fragmentManager,lifecycle) {
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

	override fun onDestroyView() {
		mBinding?.pager?.apply {
			adapter = null
		}
		adapter = null
		adapterCurrency = null
		mBinding = null
		super.onDestroyView()
	}

	override val baseViewModel: BaseViewModel
		get() = mViewModel


}