package com.paya.presentation.base
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paya.presentation.ui.errorDoalog.ErrorDialog
import com.paya.presentation.ui.errorDoalog.ErrorDialogModel
import com.paya.presentation.ui.loading.LoadingDialog
import com.paya.presentation.utils.observe

abstract class BaseBottomSheetDialogFragment <VM : BaseViewModel>: BottomSheetDialogFragment(){
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
        observe(baseViewModel.errorLiveData, ::readyError)
        observe(baseViewModel.unLoading, ::readyLoading)
    }

    private fun readyError(error: ErrorDialogModel) {
        if (errorDialog == null)
            errorDialog = ErrorDialog()
        errorDialog?.apply {
            if (!isShowing()) {
                setMessage(error = error)
                show(this@BaseBottomSheetDialogFragment.childFragmentManager, "errorTag")
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
                if (!it.isShowing())
                    loadingDialog = null
            }
            if (isLoading) {
                it.show(this@BaseBottomSheetDialogFragment.childFragmentManager, "loading dialog")
            } else {
                if (it.isShowing())
                    it.dismissAllowingStateLoss()
            }
        }
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