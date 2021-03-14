package com.paya.presentation.ui.activateForgotPassword


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentActivateBinding
import com.paya.presentation.databinding.FragmentActivateForgotBinding
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.ActivateForgotPasswordViewModel
import com.paya.presentation.viewmodel.ActivateViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ActivateForgotPasswordFragment : BaseFragment<ActivateForgotPasswordViewModel>() {
	
	private val mViewModel: ActivateForgotPasswordViewModel by viewModels()
	private lateinit var mBinding: FragmentActivateForgotBinding
	private val args by navArgs<ActivateForgotPasswordFragmentArgs>()
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = DataBindingUtil.inflate(
			inflater,
			R.layout.fragment_activate_forgot,
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
			findNavController().popBackStack()
		}
		requestHint()
	}
	
	private fun checkActivateStatus(activateResource: Resource<Any>){
		if (activateResource.status == Status.SUCCESS){

			findNavController().navigate(
				R.id.navigateToSetNewPasswordFragment
			)
		}else if (activateResource.status == Status.ERROR){
			Toast.makeText(
				requireContext(), activateResource.message, Toast.LENGTH_SHORT
			).show()
		}
	}
	
	override val baseViewModel: BaseViewModel
		get() = mViewModel

	private fun requestHint() {

		val client = context?.let {
			SmsRetriever.getClient(it /* context */) }

		val task: Task<Void> = client!!.startSmsRetriever()

		task.addOnSuccessListener(OnSuccessListener<Void?> {
			Log.d("","")
			// Successfully started retriever, expect broadcast intent
			// ...
		})

		task.addOnFailureListener(OnFailureListener {
			Log.d("","")
			// Failed to start retriever, inspect Exception for more details
			// ...
		})

	}




}