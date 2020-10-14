package com.paya.presentation.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

open class BaseFragment<VM : BaseViewModel> : Fragment(){
	
	inline fun <reified VM : BaseViewModel> viewModelProvider(
		mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
		crossinline provider: () -> VM
	) = lazy(mode) {
		ViewModelProviders.of(this, object : ViewModelProvider.Factory {
			override fun <T1 : ViewModel> create(aClass: Class<T1>) =
				provider() as T1
		}).get(VM::class.java)
	}
}