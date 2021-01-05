package com.paya.presentation.base

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.paya.presentation.R
import com.paya.presentation.utils.observe

abstract class BaseFragment<VM : BaseViewModel> : Fragment(){
	abstract val baseViewModel: BaseViewModel
	inline fun <reified VM : BaseViewModel> viewModelProvider(
		mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
		crossinline provider: () -> VM
	) = lazy(mode) {
		ViewModelProviders.of(this, object : ViewModelProvider.Factory {
			override fun <T1 : ViewModel> create(aClass: Class<T1>) =
				provider() as T1
		}).get(VM::class.java)
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		observe(baseViewModel.unAuthorizeLiveData,::unAuthorized)
	}
	
	private fun unAuthorized(message : String) {
		Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
//		activity?.findNavController(R.id.nav_host_fragment)?.navigate(
//			R.id.actionUnAuthorized
//		)
	}
}