package com.paya.presentation

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


open class BaseActivity : AppCompatActivity() {
    
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
    
}
