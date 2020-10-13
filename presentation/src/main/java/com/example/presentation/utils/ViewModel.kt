package com.example.presentation.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LifecycleOwner.observe(liveData: LiveData<T>?,action: (t: T) -> Unit) {
	liveData?.observe(this, Observer { t -> action(t) })
}