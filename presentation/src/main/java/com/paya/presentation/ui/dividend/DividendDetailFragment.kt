package com.paya.presentation.ui.dividend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentDividendDetailBinding
import com.paya.presentation.ui.dividend.fragments.DividendInformationFragment
import com.paya.presentation.ui.dividend.fragments.DividendKadalFragment
import com.paya.presentation.ui.dividend.fragments.DividendSummeryFragment
import com.paya.presentation.ui.market.adapter.DayAdapter
import com.paya.presentation.utils.BindingAdapters
import com.paya.presentation.utils.WrapContentViewPager
import com.paya.presentation.utils.shared.Point






class DividendDetailFragment : Fragment() {
	
	private lateinit var mBinding: FragmentDividendDetailBinding
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		
		// Inflate the layout for this fragment
		mBinding = DataBindingUtil.inflate(
			inflater,
			R.layout.fragment_dividend_detail,
			container,
			false
		)
		
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		setupPager()
		setupDaysRecyclerView()
		val points = mutableListOf<Point>()
		for (i in 0 until 10) {
			val value = (Math.random() * 100).toFloat()
			points.add(Point(i.toFloat(),value))
		}
		BindingAdapters.setLineChartData(mBinding.dividendDetailStock.chart,points)
	}
	
	private fun setupPager() {
		mBinding.pager.adapter = DividendPagerAdapter(childFragmentManager)
		mBinding.pager.setCurrentItem(2,false)
		mBinding.tabLayout.setupWithViewPager(mBinding.pager)
		mBinding.tabLayout.getTabAt(0)?.text = getString(R.string.kadal)
		mBinding.tabLayout.getTabAt(1)?.text = getString(R.string.summary)
		mBinding.tabLayout.getTabAt(2)?.text = getString(R.string.information)
	}
	
	private fun setupDaysRecyclerView() {
		val layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,true)
		val adapter = DayAdapter()
		mBinding.dividendDetailStock.dayRecycler.layoutManager = layoutManager
		mBinding.dividendDetailStock.dayRecycler.adapter = adapter
	}
	
	private inner class DividendPagerAdapter(f: FragmentManager) : FragmentPagerAdapter(
		f,
		BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
	) {
		
		private val fragments: ArrayList<Fragment> = ArrayList()
		private var mCurrentPosition = -1
		
		init {
			fragments.add(DividendKadalFragment())
			fragments.add(DividendSummeryFragment())
			fragments.add(DividendInformationFragment())
		}
		
		override fun setPrimaryItem(container: ViewGroup,position: Int,`object`: Any) {
			super.setPrimaryItem(container,position,`object`)
			if (position != mCurrentPosition) {
				val fragment = `object` as Fragment
				val pager: WrapContentViewPager = container as WrapContentViewPager
				if (fragment.view != null) {
					mCurrentPosition = position
					pager.measureCurrentView(fragment.requireView())
				}
			}
		}
		
		
		override fun getCount(): Int = fragments.size
		
		override fun getItem(position: Int): Fragment {
			return fragments[position]
		}
	}
	
	
}