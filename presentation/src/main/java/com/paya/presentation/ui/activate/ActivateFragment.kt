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
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentActivateBinding
import com.paya.presentation.ui.register.RegisterFragmentDirections
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.ActivateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivateFragment : BaseFragment<ActivateViewModel>() {
	
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
		args.title?.let{mViewModel.setTitle(it)}
		mBinding.viewModel = mViewModel
		mBinding.lifecycleOwner = this
		
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		observe(mViewModel.status, ::checkActivateStatus)
		mBinding.changeNumber.setOnClickListener {
			findNavController().popBackStack()
		}
	}
	
	private fun checkActivateStatus(activateResource: Resource<Any>){
		if (activateResource.status == Status.SUCCESS){
			val bundle = Bundle()
			bundle.putString("title", args.title)
			findNavController().navigate(
				R.id.navigateToSetPasswordFragment,
				bundle
			)
		}else if (activateResource.status == Status.ERROR){
			Toast.makeText(
				requireContext(), activateResource.message, Toast.LENGTH_SHORT
			).show()
		}
	}
	
	override val baseViewModel: BaseViewModel
		get() = mViewModel
	
}