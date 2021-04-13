package com.paya.soodinow


import android.app.Application
import com.paya.data.database.ObjectBox
import dagger.hilt.android.HiltAndroidApp
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump


@HiltAndroidApp
class MyApplication : Application() {

	override fun onCreate() {
		super.onCreate()
		ObjectBox.init(this)
		initFont()
	}

	private fun initFont() {
		val viewPumpBuilder = ViewPump.builder()
		val calligraphyConfig =
			CalligraphyConfig.Builder()
				.setDefaultFontPath(getString(R.string.font_farsi_regular))
				.setFontAttrId(R.attr.fontPath)
				.build()
		val viewPump = viewPumpBuilder.addInterceptor(
			CalligraphyInterceptor(
				calligraphyConfig
			)
		).build()
		ViewPump.init(viewPump)
	}
	
}