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
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentHintBinding
import com.paya.presentation.ui.hint.fragments.OpenAccount
import com.paya.presentation.utils.getVersionName
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.HintViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_update_app.*
import kotlinx.android.synthetic.main.fragment_hint.*
import kotlinx.android.synthetic.main.fragment_hint.versionTxt
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HintFragment : BaseFragment<HintViewModel>() {
	
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
		mBinding.register.setOnClickListener {
			findNavController().navigate(R.id.navigateToRegisterFragment)
		}
		mBinding.login.setOnClickListener {
			findNavController().navigate(R.id.navigateFromHintToLoginFragment)
		}
		versionTxt.text = "  نسخه  ${context?.let { getVersionName(it) }}"
	}





	override fun onDestroy() {
		super.onDestroy()
		mBinding.unbind()
	}
	
	private inner class ScreenSlidePagerAdapter(f: Fragment) : FragmentStateAdapter(f) {
		override fun getItemCount(): Int = 3
		
		override fun createFragment(position: Int): Fragment = OpenAccount()
	}
	
	override val baseViewModel: BaseViewModel
		get() = mViewModel
	
}