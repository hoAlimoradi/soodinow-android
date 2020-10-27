package com.paya.presentation.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentActivateBinding
import com.paya.presentation.databinding.FragmentMainBinding

class MainFragment : Fragment() {
	
	private lateinit var mBinding: FragmentMainBinding
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		// Inflate the layout for this fragment
		mBinding = DataBindingUtil.inflate(
			inflater,
			R.layout.fragment_main,
			container,
			false
		)
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		setNavigation()
		
	}
	
	private fun setNavigation() {
		val navHost: NavHostFragment =
			childFragmentManager.findFragmentById(R.id.mainHostFragment) as NavHostFragment?
				?: return;
		
		mBinding.bottomNav.setupWithNavController(navHost.navController)
	}
	
}