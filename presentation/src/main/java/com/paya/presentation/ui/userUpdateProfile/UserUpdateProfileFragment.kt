package com.paya.presentation.ui.userUpdateProfile

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
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentUserUpdateProfileBinding
import com.paya.presentation.ui.custom.uiInterface.BirthDateListener
import com.paya.presentation.utils.Utils
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.UserUpdateProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import ir.hamsaa.persiandatepicker.Listener
import ir.hamsaa.persiandatepicker.util.PersianCalendar

@AndroidEntryPoint
class UserUpdateProfileFragment : BaseFragment<UserUpdateProfileViewModel>(),BirthDateListener {
	
	private val mViewModel: UserUpdateProfileViewModel by viewModels()
	private lateinit var mBinding: FragmentUserUpdateProfileBinding
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding =
			DataBindingUtil.inflate(
				inflater,
				R.layout.fragment_user_update_profile,
				container,
				false
			)
		mBinding.lifecycleOwner = this
		mBinding.viewModel = mViewModel
		
		return mBinding.root
		
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		mBinding.birthDate.callBack = this
		observe(mViewModel.statusGetProfile,::readyGetProfile)
		observe(mViewModel.statusUpdateProfile,::updateGetProfile)
		
	}
	
	private fun readyGetProfile(resource: Resource<ProfileRepoModel>) {
		when (resource.status) {
			Status.ERROR -> {
				context.let {
					Toast.makeText(it,resource.message,Toast.LENGTH_SHORT).show()
				}
				findNavController().popBackStack()
			}
			else -> return
		}
	}
	
	private fun updateGetProfile(resource: Resource<ProfileRepoModel>) {
		when (resource.status) {
			Status.SUCCESS -> context.let {
				Toast.makeText(it,getString(R.string.update_profile_message),Toast.LENGTH_SHORT)
					.show()
			}
			Status.ERROR -> context.let {
				Toast.makeText(it,resource.message,Toast.LENGTH_SHORT).show()
			}
			else -> return
		}
	}
	
	override fun onSelectedDate() {
		Utils.openPersianCalender(context,mViewModel.date.get(),object : Listener {
			override fun onDateSelected(p0: PersianCalendar?) {
				mViewModel.date.set(p0)
			}
			
			override fun onDismissed() {
			
			}
			
		})
		
	}
	
	override val baseViewModel: BaseViewModel
		get() = mViewModel
	
	
}