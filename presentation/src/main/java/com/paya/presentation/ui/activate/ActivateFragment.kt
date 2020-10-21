package com.paya.presentation.ui.activate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.paya.domain.models.repo.ActivateRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentActivateBinding
import com.paya.presentation.ui.register.RegisterFragmentDirections
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.ActivateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivateFragment : Fragment() {
	
	private val mViewModel: ActivateViewModel by viewModels()
	private lateinit var mBinding: FragmentActivateBinding
	private val args by navArgs<ActivateFragmentArgs>()
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = DataBindingUtil.inflate(
			inflater,
			R.layout.fragment_activate,
			container,
			false
		)
		
		mViewModel.phoneNumber = args.phoneNumber
		mBinding.viewModel = mViewModel
		mBinding.lifecycleOwner = this
		
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		observe(mViewModel.status, ::checkActivateStatus)
		mBinding.changeNumber.setOnClickListener {
			findNavController().popBackStack(R.id.registerFragment, false)
		}
	}
	
	private fun checkActivateStatus(activateResource: Resource<Nothing>){
		if (activateResource.status == Status.SUCCESS){
			findNavController().navigate(R.id.activateFragment)
		}else if (activateResource.status == Status.ERROR){
			Toast.makeText(
				requireContext(), activateResource.message, Toast.LENGTH_SHORT
			).show()
		}
	}
	
}