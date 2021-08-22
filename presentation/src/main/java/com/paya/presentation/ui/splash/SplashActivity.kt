package com.paya.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.paya.domain.models.repo.CheckVersionRepoModel
import com.paya.domain.models.repo.ConfigRepoModel
import com.paya.domain.models.repo.ValidTokenRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.MainActivity
import com.paya.presentation.R
import com.paya.presentation.base.BaseActivity
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.ui.updateApp.UpdateAppDialog
import com.paya.presentation.utils.getVersionName
import com.paya.presentation.utils.loge
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.SplashActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SplashActivity : BaseActivity<SplashActivityViewModel>() {
    private val mViewModel: SplashActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        versionTxt.text = getVersionName(this)
        observe(mViewModel.status, ::readyCheckVersion)
        observe(mViewModel.statusValidToken, ::readyValidToken)
        observe(mViewModel.statusConfig, ::getConfig)
        observe(mViewModel.retryLiveData, ::readyRetryApi)
        mViewModel.checkVersion(getVersionName(this))
        mViewModel.getConfig()

    }

    private fun readyValidToken(resource: Resource<ValidTokenRepoModel>) {
        if (resource.status == Status.SUCCESS) {
            loge(resource.data.toString())
            startActivity(MainActivity.createIntent(this, MainActivity.PageEnum.UNKNONW))
            finish()
        }
    }
    private fun readyCheckVersion(resource: Resource<CheckVersionRepoModel>) {
        if (resource.status == Status.SUCCESS) {
            if (resource.data?.isUpdate == true) {
                val updateAppDialog = UpdateAppDialog()
                updateAppDialog.cancelDialog = {
                 mViewModel.validToken()
                }

                resource.data?.let {
                    updateAppDialog.setForce(it.isForce)
                    updateAppDialog.setLink(it.link)
                }
                updateAppDialog.show(supportFragmentManager, "updateDialog")
            } else {
                mViewModel.validToken()
            }
        }
    }


    private fun getConfig(resource: Resource<ConfigRepoModel>) {
        if (resource.status == Status.SUCCESS) {
            resource.data?.let {

            }
        }
    }
    private fun readyRetryApi(resource: Int) {
        mViewModel.checkVersion(getVersionName(this))
    }

    override val baseViewModel: BaseViewModel
        get() = mViewModel
}