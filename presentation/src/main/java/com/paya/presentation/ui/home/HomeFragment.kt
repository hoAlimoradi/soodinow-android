package com.paya.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.paya.domain.models.repo.AccountCardRepoModel
import com.paya.domain.models.repo.ExitAccountRepoModel
import com.paya.domain.models.repo.MarketRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentHomeBinding
import com.paya.presentation.ui.hint.fragments.CardAccount
import com.paya.presentation.ui.home.adapter.MarketAdapter
import com.paya.presentation.utils.BindingAdapters
import com.paya.presentation.utils.ViewPagerUtil
import com.paya.presentation.utils.observe
import com.paya.presentation.utils.setAllOnClickListener
import com.paya.presentation.utils.shared.Point
import com.paya.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : Fragment() {
	
	private lateinit var adapter: SlidePagerAdapter
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
		observe(mViewModel.getAccountCard,::checkGetAccountStatus)
		observe(mViewModel.getMarketSmallList,::checkGetMarketSmallListStatus)
		observe(mViewModel.exitAccountStatus,::readyExitAccount)
		val manager = LinearLayoutManager(context,HORIZONTAL,true)
		val adapter = MarketAdapter()
		mBinding.btnCreateAccount.setOnClickListener {
			findNavController().navigate(
				HomeFragmentDirections.navigateToCreateAccountFragment()
			)
		}
		mBinding.buttonCreateAccount.setOnClickListener {
			findNavController().navigate(
				HomeFragmentDirections.navigateToCreateAccountFragment()
			)
		}
		
		personalGroup.setAllOnClickListener {
			//mViewModel.exitAccount()
			findNavController().navigateUp()
			findNavController().navigate(
				HomeFragmentDirections.navigateToCreateWithoutRiskAccountFragment()
			)
		}
		mBinding.marketRecycleView.layoutManager = manager
		mBinding.marketRecycleView.adapter = adapter
		val dividerItemDecoration = DividerItemDecoration(context,DividerItemDecoration.HORIZONTAL)
		ResourcesCompat.getDrawable(resources,R.drawable.divider_market_row,null)?.let {
			dividerItemDecoration.setDrawable(
				it
			)
		}
		mBinding.marketRecycleView.addItemDecoration(dividerItemDecoration)
		
		setupViewPager()
		
		// TODO: 11/11/2020 AD delete
		mBinding.createAccountImage.setOnClickListener {
			mBinding.createAccountGroup.visibility = View.GONE
			mBinding.viewPagerGroup.visibility = View.GONE
			accountCard.visibility = View.VISIBLE
		}
		accountCard.setOnClickListener {
			mBinding.createAccountGroup.visibility = View.GONE
			mBinding.viewPagerGroup.visibility = View.VISIBLE
			accountCard.visibility = View.GONE
		}
		pager.setOnClickListener {
			mBinding.createAccountGroup.visibility = View.VISIBLE
			mBinding.viewPagerGroup.visibility = View.GONE
			accountCard.visibility = View.GONE
		}
			mBinding.alarm.setOnClickListener {
			findNavController().navigateUp()
			findNavController().navigate(
				HomeFragmentDirections.navigateToActivitiesReportFragment()
			)
		}
		
		setChartData()
	}
	
	private fun setChartData() {
		val points = mutableListOf<Point>()
		for (i in 0 until 10) {
			val value = (Math.random() * 100).toFloat()
			points.add(Point(i.toFloat(),value))
		}
		BindingAdapters.setLineAccountChartData(mBinding.accountCard.chart,points)

	}
	
	private fun readyExitAccount(resource: Resource<ExitAccountRepoModel>) {
		when (resource.status) {
			Status.SUCCESS ->
				resource.data.let {
					if (!it!!.existing) {
						findNavController().navigateUp()
						findNavController().navigate(
							HomeFragmentDirections.navigateToCreateWithoutRiskAccountFragment()
						)
					} else {
						context.let {
							Toast.makeText(it,
								if(resource.message.isNullOrEmpty()) getString(R.string.create_account_not_compelet) else resource.message,
								Toast.LENGTH_SHORT).show()
						}
					}
				}
			Status.ERROR -> context.let {
				Toast.makeText(it,resource.message,Toast.LENGTH_SHORT).show()
			}
			else -> return
		}
	}
	
	private fun checkGetAccountStatus(accountResource: Resource<AccountCardRepoModel>) {
	
	}
	
	private fun checkGetMarketSmallListStatus(marketResource: Resource<MarketRepoModel>) {
	
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
	
	private inner class SlidePagerAdapter(f: Fragment) : FragmentStateAdapter(f) {
		override fun getItemCount(): Int = 3
		
		override fun createFragment(position: Int): Fragment = CardAccount()
	}
}