package com.paya.presentation.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.paya.presentation.R
import com.paya.presentation.ui.farabi.FarabiAuthActivity
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
		observe(baseViewModel.unFarabiAuth, ::farabiAuth)
	}
	
	private fun unAuthorized(message : String) {
		Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
		activity?.findNavController(R.id.nav_host_fragment)?.navigate(
			R.id.actionUnAuthorized
		)
	}

	fun getFindViewController(): NavController? {
		return activity?.findNavController(R.id.nav_host_fragment)
	}

	private fun farabiAuth(param: Unit) {
		val intent = Intent(context, FarabiAuthActivity::class.java)
		resultLauncher.launch(intent)
	}

	var resultLauncher =
		registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
			if (result.resultCode === Activity.RESULT_OK) {
				farabiAccessToken()
			}
		}

	open fun farabiAccessToken() {

	}
}