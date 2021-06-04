package com.paya.presentation.ui.dividend.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentDividendKadalBinding
import com.paya.presentation.ui.dividend.adapter.KadalAdapter


class DividendKadalFragment : Fragment() {
	
	private lateinit var mBinding: FragmentDividendKadalBinding
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
	}
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = DataBindingUtil.inflate(
			inflater,
			R.layout.fragment_dividend_kadal,
			container,
			false
		)
		
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		setupKadalAdapter()
	}
	override fun onDestroy() {
		super.onDestroy()
		mBinding.unbind()
	}
	private fun setupKadalAdapter() {
		val manager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
		mBinding.kadalRecyclerView.layoutManager = manager
		mBinding.kadalRecyclerView.adapter = KadalAdapter()
		mBinding.kadalRecyclerView.isNestedScrollingEnabled = false
		val dividerItemDecoration = DividerItemDecoration(context, LinearLayout.VERTICAL)
		context?.let { it ->
			ContextCompat.getDrawable(it,R.drawable.divider_line)?.let {
				dividerItemDecoration.setDrawable(
					it
				)
			}
		}
		mBinding.kadalRecyclerView.addItemDecoration(dividerItemDecoration)
	}
}