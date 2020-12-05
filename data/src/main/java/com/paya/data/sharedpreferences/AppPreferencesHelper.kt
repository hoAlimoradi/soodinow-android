package com.paya.data.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import com.paya.domain.tools.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppPreferencesHelper @Inject constructor(
    @ApplicationContext private val context: Context
) :
    PreferenceHelper {
    private val mPrefs: SharedPreferences = context.getSharedPreferences("Soodinow_Pref", Context.MODE_PRIVATE)

    override fun getAccessToken(): String {
        return mPrefs.getString(PREF_KEY_ACCESS_TOKEN,null) ?: ""
    }

    override fun setAccessToken(accessToken: String) {
        mPrefs.edit()
            .putString(PREF_KEY_ACCESS_TOKEN, accessToken)
            .apply()
    }
    
    override fun getMobile(): Resource<String> {
        return Resource.success(mPrefs.getString(PREF_KEY_MOBILE,null) ?: "")
    }
    
    override fun setMobile(mobile: String) {
        mPrefs.edit()
            .putString(PREF_KEY_MOBILE, mobile)
            .apply()
    }
    
    companion object {
        private const val PREF_KEY_ACCESS_TOKEN: String = "PREF_KEY_ACCESS_TOKEN"
        private const val PREF_KEY_MOBILE: String = "PREF_KEY_MOBILE"
    }

}
