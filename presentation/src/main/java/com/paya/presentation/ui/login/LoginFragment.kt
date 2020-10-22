package com.paya.presentation.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentLoginBinding
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
	
	private lateinit var mBinding: FragmentLoginBinding
	private val mViewModel: LoginViewModel by viewModels()
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = DataBindingUtil.inflate(
			inflater,
			R.layout.fragment_login,
			container,
			false
		)
		
		mBinding.viewModel = mViewModel
		mBinding.lifecycleOwner = this
		
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		observe(mViewModel.loginResource, ::checkLoginStatus)
	}
	
	private fun checkLoginStatus(resource: Resource<Any>){
		if (resource.status == Status.SUCCESS){
			findNavController().navigate(R.id.homeFragment)
		}else if (resource.status == Status.ERROR){
			Toast.makeText(
				requireContext(), resource.message, Toast.LENGTH_SHORT
			).show()
		}
	}
	
}