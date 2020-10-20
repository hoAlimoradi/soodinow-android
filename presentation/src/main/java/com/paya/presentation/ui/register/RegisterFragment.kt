package com.paya.presentation.ui.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.paya.domain.models.repo.RegisterRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentRegisterBinding
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
	
	private val mViewModel: RegisterViewModel by viewModels()
	private lateinit var mBinding: FragmentRegisterBinding
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = DataBindingUtil.inflate(
			inflater,
			R.layout.fragment_register,
			container,
			false
		)
		
		mBinding.viewModel = mViewModel
		
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		observe(mViewModel.registerStatus, ::checkRegisterStatus)
	}
	
	private fun checkRegisterStatus(registerResource: Resource<RegisterRepoModel>){
		Log.d("checkRegisterStatus", registerResource.status.toString())
		if (registerResource.status == Status.SUCCESS){
			findNavController().navigate(
				RegisterFragmentDirections.navigateToActivateFragment(mViewModel.phoneNumber.get()!!)
			)
			onDestroy()
		}else if (registerResource.status == Status.ERROR){
			Toast.makeText(
				requireContext(), registerResource.message, Toast.LENGTH_SHORT
			).show()
		}
	}
	
}