package com.paya.presentation.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.paya.domain.models.repo.GetAuthLinkRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.MainActivity
import com.paya.presentation.R
import com.paya.presentation.ui.errorDoalog.ErrorDialog
import com.paya.presentation.ui.errorDoalog.ErrorDialogModel
import com.paya.presentation.ui.loading.LoadingDialog
import com.paya.presentation.utils.observe
import com.paya.presentation.utils.openUrl
import io.github.inflationx.viewpump.ViewPumpContextWrapper


abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {
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
        observe(baseViewModel.unExistProfileUser, ::profileExist)
        observe(baseViewModel.errorLiveData, ::readyError)
        observe(baseViewModel.unLoading, ::readyLoading)
    }
    private fun profileExist(param: Unit) {
        try {
            findNavController(R.id.nav_host_fragment).navigate(
                R.id.firstInformation
            )
        } catch (e: Exception) {
           /* startActivity(Intent(this@BaseActivity, MainActivity::class.java))
            finish()*/
        }
    }
    private fun unAuthorized(message: String) {
            startActivity(MainActivity.createIntent(this, MainActivity.PageEnum.LOGIN))
            finish()

    }

    var resultLauncher : ActivityResultLauncher<Intent>? =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode === Activity.RESULT_OK) {
                farabiAccessToken()
            }
        }
    private fun farabiAuth(resource: Resource<GetAuthLinkRepoModel>) {
        if (resource.status == Status.SUCCESS) {
            resource.data?.let { openUrl(it.link) }
        }
    }

    private fun readyError(error: ErrorDialogModel) {
        if (errorDialog == null)
            errorDialog = ErrorDialog()
        errorDialog?.apply {
            if (!isShowing()) {
                setMessage(error)
                show(supportFragmentManager, "errorTag")
            }
            onDismiss = {
                if (!isShowing())
                    errorDialog = null
            }
        }
    }


    fun farabiAccessToken() {

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
                it.show(supportFragmentManager, "loading dialog")
            } else {
                if (it.isShowing())
                    it.dismissAllowingStateLoss()
            }
        }

    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.let { ViewPumpContextWrapper.wrap(it) })
    }

    override fun onDestroy() {
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
        resultLauncher?.apply{unregister()}
        resultLauncher = null
        super.onDestroy()
    }
}