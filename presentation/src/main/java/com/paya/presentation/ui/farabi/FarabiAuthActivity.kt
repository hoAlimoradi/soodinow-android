package com.paya.presentation.ui.farabi

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import android.webkit.WebView
import androidx.activity.viewModels
import com.hadiidbouk.appauthwebview.AppAuthWebView
import com.hadiidbouk.appauthwebview.AppAuthWebViewData
import com.hadiidbouk.appauthwebview.IAppAuthWebViewListener
import com.paya.domain.models.repo.FarabiInfoRepoModel
import com.paya.domain.models.repo.FarabiTokenRepoModel
import com.paya.domain.models.repo.UserFarabiRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.base.BaseActivity
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.ActivityFarabiAuthBinding
import com.paya.presentation.utils.observe
import com.paya.presentation.utils.shortToast
import com.paya.presentation.viewmodel.FarabiAuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_farabi_auth.*
import kotlinx.android.synthetic.main.toolbar.view.*
import net.openid.appauth.AuthState



@AndroidEntryPoint
class FarabiAuthActivity : BaseActivity<FarabiAuthViewModel>() {
    private var appAuthWebView: AppAuthWebView? = null
    private var mBinding: ActivityFarabiAuthBinding? = null
    private var mData: AppAuthWebViewData? = null
    private val mViewModel: FarabiAuthViewModel by viewModels()
    private var webView: WebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityFarabiAuthBinding.inflate(layoutInflater)
        setContentView(mBinding?.root)
        webView = WebView(applicationContext)
        webViewFrameLayout.addView(webView)
        observe(mViewModel.status, ::readyFarabiAuth)
        observe(mViewModel.statusGetUserFarabi, ::readyUserFarabi)
        observe(mViewModel.statusFarabiInfo, ::readyFarabiInfo)
        createAppAuth()
        toolbar.backButton.setOnClickListener {
           onBackPressed()
        }

    }

    private fun createAppAuth() {
        mBinding?.apply {
            webView?.apply {
                clearCache(true)
                clearFormData()
            }
        }
        clearCookies(this)
        mData = AppAuthWebViewData()
        mData?.apply {
            clientId = ""
            discoveryUri = ""
            scope = ""
            authorizationEndpointUri = ""
            redirectLoginUri = ""
            tokenEndpointUri = ""
            responseType = ""
            isGenerateCodeVerifier = true


            //Todo: delete after refactoring the code
            registrationEndpointUri = ""
            redirectLogoutUri = ""
            clientSecret = ""
            webView?.let { webView ->
                appAuthWebView = AppAuthWebView.Builder()
                    .webView(webView)
                    .authData(this)
                    .listener(object : IAppAuthWebViewListener {
                        override fun onUserAuthorize(p0: AuthState?) {
                            p0?.accessToken?.let {
                                // mViewModel.setToken(it)
                                mViewModel.getUserFarabi("Bearer $it")
                                val clipboard: ClipboardManager =
                                    getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("token", it)
                                clipboard.setPrimaryClip(clip)
                            }
                        }

                        override fun onLogoutFinish() {
                            setResult(Activity.RESULT_OK)
                            finish()
                        }

                        override fun showLoadingLayout() {
//                        includeLoading.loading.visibility = View.VISIBLE
                        }

                        override fun hideLoadingLayout() {
                            //    includeLoading.loading.visibility = View.GONE
                        }

                        override fun hideConnectionErrorLayout() {

                        }

                        override fun showConnectionErrorLayout() {
                            shortToast("مشکلی پیش آمده دوباره تلاش کنید")

                        }

                    }).build()
                appAuthWebView?.performLoginRequest()
            }
        }
    }

    private fun readyFarabiAuth(resource: Resource<FarabiTokenRepoModel>) {
        when (resource.status) {
            Status.SUCCESS -> {
                setResult(Activity.RESULT_OK)
                finish()
            }
            else -> return
        }
    }

    private fun readyFarabiInfo(resource: Resource<FarabiInfoRepoModel>) {
        when (resource.status) {
            Status.SUCCESS -> {
                setResult(Activity.RESULT_OK)
                finish()
            }
            else -> return
        }
        appAuthWebView?.let {
            it.performLogoutRequest()

        }
    }

    private fun readyUserFarabi(resource: Resource<UserFarabiRepoModel>) {
        when (resource.status) {
            Status.SUCCESS -> {
                resource.data?.let { mViewModel.setFarabiInfo(it) }
            }
            else -> return
        }
    }

    override fun onDestroy() {
        mData = null
        webViewFrameLayout.removeAllViews()
        webView?.apply{destroy()}
        webView = null
        appAuthWebView = null
        mBinding = null
        super.onDestroy()
    }
    override val baseViewModel: BaseViewModel
        get() = mViewModel

    @SuppressWarnings("deprecation")
    fun clearCookies(context: Context?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null)
            CookieManager.getInstance().flush()
        } else if (context != null) {
            val cookieSyncManager = CookieSyncManager.createInstance(context)
            cookieSyncManager.startSync()
            val cookieManager: CookieManager = CookieManager.getInstance()
            cookieManager.removeAllCookie()
            cookieManager.removeSessionCookie()
            cookieSyncManager.stopSync()
            cookieSyncManager.sync()
        }
    }
}
