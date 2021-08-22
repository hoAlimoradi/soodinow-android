package com.paya.presentation.utils

import android.util.Log
import com.paya.presentation.BuildConfig

fun Any.logd(message: String?) {
    if (BuildConfig.DEBUG){
        Log.d(" ", "   --> $message")
        //Log.d(this::class.java.simpleName, "   --> $message")
    }

}

fun Any.loge(message: String?) {
    if (BuildConfig.DEBUG){
        Log.e("", " -------------------------------------------------------------------------")
        Log.e(this::class.java.toString(), " --> $message")
    }
}