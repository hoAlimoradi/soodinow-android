package com.example.mvvm_arch

import android.app.Application
import com.example.data.database.ObjectBox
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application(){
	
	override fun onCreate() {
		super.onCreate()
		ObjectBox.init(this)
	}
	
}