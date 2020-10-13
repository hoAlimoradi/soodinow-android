package com.example.data.database

import android.content.Context
import com.example.domain.models.local.MyObjectBox
import io.objectbox.BoxStore

object ObjectBox {
	lateinit var boxStore: BoxStore
		private set

	fun init(context: Context) {
		boxStore = MyObjectBox.builder()
			.androidContext(context.applicationContext)
			.build()
	}
}