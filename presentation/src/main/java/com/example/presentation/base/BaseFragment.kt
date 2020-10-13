package com.example.presentation.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

open class BaseFragment<VM : BaseViewModel> : Fragment(){
	
//	var viewModelFactory: ViewModelProvider.Factory? = null
//	lateinit var viewModel: VM

//	inline fun <reified V : BaseViewModel> BaseFragment<V>.createViewModel() {
//		viewModel = ViewModelProviders.of(this, viewModelFactory).get(V::class.java)
//	}
//
//
//	override fun <T : ViewModel> create(modelClass: Class<T>): T {
//		return viewModelProviders[modelClass]!!.get() as T
//	}
	
	inline fun <reified VM : BaseViewModel> BaseFragment<VM>.viewModelProvider(
		mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
		crossinline provider: () -> VM
	) = lazy(mode) {
		ViewModelProviders.of(this, object : ViewModelProvider.Factory {
			override fun <T1 : ViewModel> create(aClass: Class<T1>) =
				provider() as T1
		}).get(VM::class.java)
	}
}