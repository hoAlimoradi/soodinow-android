package com.paya.presentation.ui.hint

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentHintBinding
import com.paya.presentation.ui.hint.fragments.OpenAccount
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.HintViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_hint.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HintFragment : Fragment() {
	
	private lateinit var mBinding: FragmentHintBinding
	private val mViewModel: HintViewModel by viewModels()
	private var selectedIndex = 0
	private lateinit var adapter: ScreenSlidePagerAdapter
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = DataBindingUtil.inflate(
			inflater,
			R.layout.fragment_hint,
			container,
			false
		)
		
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		observe(mViewModel.userState, ::checkUserStatus)
		mBinding.register.setOnClickListener {
			findNavController().navigate(R.id.navigateToRegisterFragment)
		}
		mBinding.login.setOnClickListener {
			findNavController().navigate(R.id.navigateFromHintToLoginFragment)
		}
		setupViewPager()
	}
	
	private fun setupViewPager() {
		adapter = ScreenSlidePagerAdapter(this)
		mBinding.pager.adapter = adapter
		
		mBinding.pager.registerOnPageChangeCallback(
			object : ViewPager2.OnPageChangeCallback() {
				override fun onPageSelected(position: Int) {
					super.onPageSelected(position)
					selectedIndex = position
					checkSelectedIndex()
				}
			}
		)
		
		mBinding.imgLeftArrow.setOnClickListener {
			pager.setCurrentItem(--selectedIndex, true)
		}
		mBinding.imgRightArrow.setOnClickListener {
			pager.setCurrentItem(++selectedIndex, true)
		}
	}
	
	private fun checkSelectedIndex() {
		mBinding.imgRightArrow.visibility = View.VISIBLE
		mBinding.imgLeftArrow.visibility = View.VISIBLE
		
		if (selectedIndex == 0){
			mBinding.imgLeftArrow.visibility = View.GONE
		}else if (selectedIndex == adapter.itemCount - 1){
			mBinding.imgRightArrow.visibility = View.GONE
		}
	}
	
	private fun checkUserStatus(userStatus: HintViewModel.UserState){
		when(userStatus){
			HintViewModel.UserState.IS_HINT_SHOWED -> {
				findNavController().navigate(R.id.navigateToRegisterFragment)
			}
			HintViewModel.UserState.IS_PASSWORD_SET -> {
				findNavController().navigate(R.id.navigateFromHintToLoginFragment)
			}
			HintViewModel.UserState.NONE -> return
		}
	}
	
	private inner class ScreenSlidePagerAdapter(f: Fragment) : FragmentStateAdapter(f) {
		override fun getItemCount(): Int = 3
		
		override fun createFragment(position: Int): Fragment = OpenAccount()
	}
	
}