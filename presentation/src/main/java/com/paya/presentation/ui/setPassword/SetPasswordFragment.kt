package com.paya.presentation.ui.setPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.paya.domain.models.repo.SetPasswordRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentSetPasswordBinding
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.SetPasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetPasswordFragment : Fragment() {
	
	private lateinit var mBinding: FragmentSetPasswordBinding
	private val mViewModel: SetPasswordViewModel by viewModels()
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = DataBindingUtil.inflate(
			inflater,
			R.layout.fragment_set_password,
			container,
			false
		)
		
		mBinding.viewModel = mViewModel
		
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		observe(mViewModel.setPasswordResource, ::checkSetPasswordStatus)
	}
	
	private fun checkSetPasswordStatus(resource: Resource<SetPasswordRepoModel>){
		if (resource.status == Status.SUCCESS){
//			findNavController().navigate(R.id.setPasswordFragment)
			Toast.makeText(
				requireContext(), resource.data?.message ?: "SUCCESSS", Toast.LENGTH_SHORT
			).show()
		}else if (resource.status == Status.ERROR){
			Toast.makeText(
				requireContext(), resource.message, Toast.LENGTH_SHORT
			).show()
		}
	}
	
}