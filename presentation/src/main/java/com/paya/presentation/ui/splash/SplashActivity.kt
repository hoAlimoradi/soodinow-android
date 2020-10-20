package com.paya.presentation.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.paya.presentation.MainActivity
import com.paya.presentation.R
import kotlinx.coroutines.delay

class SplashActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_splash)
		
		lifecycleScope.launchWhenStarted {
			delay(3000)
			startActivity(Intent(this@SplashActivity, MainActivity::class.java))
			finish()
		}
		
	}
}