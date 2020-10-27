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
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentHomeBinding
import com.paya.presentation.databinding.FragmentMainBinding

class HomeFragment : Fragment() {
	
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
		
		
	}
	
}