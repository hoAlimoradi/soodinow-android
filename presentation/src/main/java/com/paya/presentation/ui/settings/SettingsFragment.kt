package com.paya.presentation.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.paya.domain.models.repo.ProfileRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentSettingsBinding
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_settings.*

@AndroidEntryPoint
class SettingsFragment : Fragment() {
	
	private val mViewModel: SettingViewModel by viewModels()
	private lateinit var mBinding: FragmentSettingsBinding
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_settings,container,false)
		mBinding.lifecycleOwner = this
		mBinding.viewModel = mViewModel
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		observe(mViewModel.status,::ready)
		changePassword.setOnClickListener {
			findNavController().navigate(
				SettingsFragmentDirections.navigateToChangePasswordFragment()
			)
		}
		myProfile.setOnClickListener {
			findNavController().navigate(
				SettingsFragmentDirections.navigateToUserUpdateProfileFragment()
			)
		}
		share.setOnClickListener {
			findNavController().navigate(
				SettingsFragmentDirections.navigateToIntroduceFriendsFragment()
			)
		}
	}
	
	private fun ready(resource: Resource<ProfileRepoModel>) {
		when (resource.status) {
			Status.SUCCESS -> mViewModel.mobile.set(resource.data?.mobile)
			Status.ERROR -> context.let {
				Toast.makeText(it,resource.message,Toast.LENGTH_SHORT).show()
			}
			else -> return
			
		}
		
	}
	
}