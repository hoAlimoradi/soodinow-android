package com.paya.presentation.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentMainBinding

class MainFragment : Fragment() {

	private var mBinding: FragmentMainBinding? = null

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
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
		return mBinding?.let { it.root }
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setNavigation()

	}

	override fun onDestroyView() {
		mBinding = null
		super.onDestroyView()
	}

	private fun setNavigation() {
		val navHost: NavHostFragment =
			childFragmentManager.findFragmentById(R.id.mainHostFragment) as NavHostFragment?
				?: return

		mBinding?.apply { bottomNav.setupWithNavController(navHost.navController) }
	}

}