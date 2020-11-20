package com.paya.presentation.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentProfileBinding
import com.paya.presentation.ui.hint.fragments.CardAccount
import com.paya.presentation.utils.ViewPagerUtil
import com.paya.presentation.viewmodel.ProfileViewModel

class ProfileFragment : Fragment() {
	
	private lateinit var mBinding: FragmentProfileBinding
	private lateinit var adapter: SlidePagerAdapter
	private val viewModel: ProfileViewModel by viewModels()
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = DataBindingUtil.inflate(
			inflater,
			R.layout.fragment_profile,
			container,
			false
		)
		
		mBinding.lifecycleOwner = this
		mBinding.viewModel = viewModel
		
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		setupViewPager()
		viewModel.getPoints()
		mBinding.myRadioGroup.setOnCheckedChangeListener { _,_ ->
			viewModel.getPoints()
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
	
	private inner class SlidePagerAdapter(f: Fragment) : FragmentStateAdapter(f) {
		override fun getItemCount(): Int = 3
		
		override fun createFragment(position: Int): Fragment = CardAccount()
	}
	
}