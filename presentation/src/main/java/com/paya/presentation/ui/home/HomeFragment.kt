package com.paya.presentation.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentHomeBinding
import com.paya.presentation.databinding.FragmentMainBinding
import com.paya.presentation.ui.hint.fragments.CardAccount
import com.paya.presentation.ui.hint.fragments.OpenAccount
import com.paya.presentation.utils.ViewPagerUtil
import kotlinx.android.synthetic.main.fragment_hint.*

class HomeFragment : Fragment() {
	
	private lateinit var adapter: SlidePagerAdapter
	private lateinit var mBinding: FragmentHomeBinding
	
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
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		val manager = LinearLayoutManager(context,HORIZONTAL,true)
		val adapter = MarketAdapter()
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