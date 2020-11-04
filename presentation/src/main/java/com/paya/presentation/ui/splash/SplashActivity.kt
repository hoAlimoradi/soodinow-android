package com.paya.presentation.ui.splash

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.paya.presentation.BaseActivity
import com.paya.presentation.MainActivity
import com.paya.presentation.R
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.delay


class SplashActivity : BaseActivity() {
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
}