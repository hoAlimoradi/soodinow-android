package com.paya.soodinow

import android.app.Application
import com.paya.data.database.ObjectBox
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application(){
	
	override fun onCreate() {
		super.onCreate()
		ObjectBox.init(this)
	}
	
}