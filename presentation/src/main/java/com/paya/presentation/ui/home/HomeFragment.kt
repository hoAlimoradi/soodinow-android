package com.paya.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.paya.domain.models.repo.CurrencyPriceRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentHomeBinding
import com.paya.presentation.ui.cardAccount.NewCardAccountFragment
import com.paya.presentation.ui.home.adapter.MarketAdapter
import com.paya.presentation.utils.ViewPagerUtil
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

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
		super.onViewCreated(view,savedInstanceState)
		observe(mViewModel.currencyPrice, ::onPricesReady)
		val manager = LinearLayoutManager(context)
		adapterCurrency = MarketAdapter()
		mBinding.marketRecycleView.adapter = adapterCurrency
		mBinding.marketRecycleView.layoutManager = manager
		mViewModel.getCurrencyPrices()
		mBinding.whySoodinowMoreButton.setOnClickListener {
			getFindViewController()?.navigate(R.id.whySoodinow)
		}


		/*personalGroup.setAllOnClickListener {
			//mViewModel.exitAccount()
			findNavController().navigateUp()
			findNavController().navigate(
				HomeFragmentDirections.navigateToCreateWithoutRiskAccountFragment()
			)
		}*/
		setupViewPager()

			mBinding.alarm.setOnClickListener {
			findNavController().navigateUp()
			findNavController().navigate(
				HomeFragmentDirections.navigateToActivitiesReportFragment()
			)
		}
		

	}

	
	private fun setupViewPager() {
		adapter = SlidePagerAdapter(this)
		mBinding.pager.offscreenPageLimit = 1
		mBinding.pager.adapter = adapter
		mBinding.pager.setPadding(
			resources.getDimension(R.dimen.viewpager_item_padding).toInt(),
			0,
			resources.getDimension(R.dimen.viewpager_item_padding).toInt(),0
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
			Status.ERROR -> {
				Toast.makeText(
					requireContext(),
					resource.message ?: "خطایی رخ داده است",
					Toast.LENGTH_SHORT
				).show()
			}
			
			else -> return
		}
	}
	
	private inner class SlidePagerAdapter(f: Fragment) : FragmentStateAdapter(f) {
		override fun getItemCount(): Int = 3
		
		override fun createFragment(position: Int): Fragment = NewCardAccountFragment()
	}
	
	override val baseViewModel: BaseViewModel
		get() = mViewModel
	
	
}