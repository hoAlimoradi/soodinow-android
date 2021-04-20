package com.paya.presentation.ui.farabi

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.hadiidbouk.appauthwebview.AppAuthWebView
import com.hadiidbouk.appauthwebview.AppAuthWebViewData
import com.hadiidbouk.appauthwebview.IAppAuthWebViewListener
import com.paya.domain.models.repo.FarabiInfoRepoModel
import com.paya.domain.models.repo.FarabiTokenRepoModel
import com.paya.domain.models.repo.UserFarabiRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
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
    private lateinit var mBinding: ActivityFarabiAuthBinding
    private lateinit var mData: AppAuthWebViewData
    private val mViewModel: FarabiAuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_farabi_auth)
        setContentView(mBinding.root)
        observe(mViewModel.status, ::readyFarabiAuth)
        observe(mViewModel.statusGetUserFarabi, ::readyUserFarabi)
        observe(mViewModel.statusFarabiInfo, ::readyFarabiInfo)
        createAppAuth()
        toolbar.backButton.setOnClickListener {
            finish()
        }

    }

    private fun createAppAuth() {
        mData = AppAuthWebViewData()
        with(mData) {
            clientId = "soodinow.mobile"
            discoveryUri = "https://auth.farabixo.com/.well-known/openid-configuration"
            scope = "tse_api base_api"
            authorizationEndpointUri = "https://auth.farabixo.com/connect/authorize"
            redirectLoginUri = "http://api.soodinow.com:5000/callback.html"
            tokenEndpointUri = "https://auth.farabixo.com/connect/token"
            responseType = "code"
            isGenerateCodeVerifier = true


            //Todo: delete after refactoring the code
            registrationEndpointUri = ""
            redirectLogoutUri = ""
            clientSecret = ""

            AppAuthWebView.Builder()
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
                        shortToast("Error")

                    }

                })
                .build().apply {
                    performLoginRequest()
                }
        }
    }

    private fun readyFarabiAuth(resource: Resource<FarabiTokenRepoModel>) {
        when (resource.status) {
            Status.SUCCESS -> {
                setResult(Activity.RESULT_OK)
                finish()
            }
            Status.ERROR -> resource.message?.let { shortToast(it) }
            else -> return
        }
    }

    private fun readyFarabiInfo(resource: Resource<FarabiInfoRepoModel>) {
        when (resource.status) {
            Status.SUCCESS -> {
                setResult(Activity.RESULT_OK)
                finish()
            }
            Status.ERROR -> resource.message?.let { shortToast(it) }
            else -> return
        }
    }

    private fun readyUserFarabi(resource: Resource<UserFarabiRepoModel>) {
        when (resource.status) {
            Status.SUCCESS -> {
                resource.data?.let { mViewModel.setFarabiInfo(it) }
            }
            Status.ERROR -> resource.message?.let { shortToast(it) }
            else -> return
        }
    }

    override val baseViewModel: BaseViewModel
        get() = mViewModel


}
