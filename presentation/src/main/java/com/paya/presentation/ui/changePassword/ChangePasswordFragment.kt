package com.paya.presentation.ui.changePassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.paya.domain.models.repo.ChangePasswordRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentChangePasswordBinding
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.ChangePasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment : BaseFragment<ChangePasswordViewModel>() {
	
	private val mViewModel: ChangePasswordViewModel by viewModels()
	private lateinit var mBinding: FragmentChangePasswordBinding
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
	}
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding =
			DataBindingUtil.inflate(inflater,R.layout.fragment_change_password,container,false)
		mBinding.lifecycleOwner = this
		mBinding.viewModel = mViewModel
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		observe(mViewModel.status,::ready)
		mBinding.toolbar.backButton.setOnClickListener {
			findNavController().popBackStack()
		}
		
	}
	
	private fun ready(resource: Resource<ChangePasswordRepoModel>) {
		when (resource.status) {
			Status.SUCCESS -> {
				context.let {
					Toast.makeText(it,resource.data!!.message,Toast.LENGTH_SHORT).show()
				}
				activity.let{
					it!!.findNavController(R.id.nav_host_fragment).navigate(
						R.id.hintFragment
					)
				}
				
			}

			else -> return
		}
		
	}
	
	override val baseViewModel: BaseViewModel
		get() = mViewModel
	
}