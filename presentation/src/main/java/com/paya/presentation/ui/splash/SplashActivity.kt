package com.paya.presentation.ui.splash

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.paya.presentation.MainActivity
import com.paya.presentation.R
import com.paya.presentation.base.BaseActivity
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.viewmodel.SpalshActivityViewModel
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.delay


class SplashActivity : BaseActivity<SpalshActivityViewModel>() {
	private val mViewModel : SpalshActivityViewModel by viewModels()
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_splash)
		
		setVersionName()
		
		lifecycleScope.launchWhenStarted {
			delay(3000)
			startActivity(Intent(this@SplashActivity,MainActivity::class.java))
			finish()
		}
		
	}
	
	private fun setVersionName() {
		try {
			val pInfo: PackageInfo =
				packageManager.getPackageInfo(packageName,0)
			val version = pInfo.versionName
			versionTxt.text = version
		} catch (e: PackageManager.NameNotFoundException) {
			e.printStackTrace()
		}
	}

	override val baseViewModel: BaseViewModel
		get() = mViewModel
}