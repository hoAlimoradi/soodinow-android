package com.paya.soodinow

import android.app.Application
import com.paya.data.database.ObjectBox
import dagger.hilt.android.HiltAndroidApp
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

@HiltAndroidApp
class MyApplication: Application() {
	
	override fun onCreate() {
		super.onCreate()
		ObjectBox.init(this)
		initFont()
	}
	
	private fun initFont() {
		CalligraphyConfig.initDefault(
			CalligraphyConfig.Builder()
				.setDefaultFontPath(getString(R.string.font_farsi_regular))
				.setFontAttrId(R.attr.fontPath)
				.build()
		)
	}
	
}