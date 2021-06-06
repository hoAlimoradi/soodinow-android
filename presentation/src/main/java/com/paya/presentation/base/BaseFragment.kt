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
import com.paya.presentation.R
import com.paya.presentation.ui.errorDoalog.ErrorDialog
import com.paya.presentation.ui.farabi.FarabiAuthActivity
import com.paya.presentation.ui.loading.LoadingDialog
import com.paya.presentation.utils.observe

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {
	abstract val baseViewModel: BaseViewModel
	private var errorDialog: ErrorDialog? = null
	private var loadingDialog: LoadingDialog? = null
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
		observe(baseViewModel.unAuthorizeLiveData, ::unAuthorized)
		observe(baseViewModel.unFarabiAuth, ::farabiAuth)
		observe(baseViewModel.errorLiveData, ::readyError)
		observe(baseViewModel.unLoading, ::readyLoading)
	}

	private fun unAuthorized(message: String) {
		Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
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

	fun readyError(error: String) {
		if (errorDialog == null)
			errorDialog = ErrorDialog()
		errorDialog?.apply {
			if (!isShowing()) {
				setMessage(error)
				show(this@BaseFragment.childFragmentManager, "errorTag")
			}
			onDismiss = {
				if (!isShowing())
					errorDialog = null
			}
		}

	}

	private fun readyLoading(isLoading: Boolean) {
		if (loadingDialog == null&&isLoading)
			loadingDialog = LoadingDialog()
		loadingDialog?.let {
			it.onDismiss = {
				if (!it.isShowing() && !isLoading)
					loadingDialog = null
			}
			if (isLoading) {
				it.show(childFragmentManager, "loading dialog")
			} else {
				if (it.isShowing())
					it.dismissAllowingStateLoss()
			}
		}
	}

	var resultLauncher =
		registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
			if (result.resultCode === Activity.RESULT_OK) {
				farabiAccessToken()
			}
		}

	open fun farabiAccessToken() {

	}

	override fun onDestroyView() {
		errorDialog?.apply {
			dialog?.let {
				if (it.isShowing)
					dismissAllowingStateLoss()
			}
		}
		errorDialog = null
		loadingDialog?.let {
			if (it.isShowing())
				it.dismissAllowingStateLoss()
		}
		loadingDialog = null
		super.onDestroyView()
	}

}