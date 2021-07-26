package com.paya.data.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import com.paya.domain.models.repo.GetAppLinkRepoModel
import com.paya.domain.tools.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppPreferencesHelper @Inject constructor(
    @ApplicationContext private val context: Context
) :
    PreferenceHelper {
    private val mPrefs: SharedPreferences = context.getSharedPreferences("Soodinow_Pref", Context.MODE_PRIVATE)

    override fun getAccessToken(): String {
        val token = mPrefs.getString(PREF_KEY_ACCESS_TOKEN,null) ?: ""
        return if(token.isEmpty()) "" else "Bearer $token"
    }

    override fun isLogin(): Boolean {
        return getRefreshToken().isNotEmpty()
    }

    override fun getRefreshToken(): String {
        return mPrefs.getString(PREF_KEY_REFRESH_TOKEN,null) ?: ""
    }

    override fun setAccessToken(accessToken: String) {
        mPrefs.edit()
            .putString(PREF_KEY_ACCESS_TOKEN, accessToken)
            .apply()
    }

    override fun setRefreshToken(token: String) {
        mPrefs.edit()
            .putString(PREF_KEY_REFRESH_TOKEN, token)
            .apply()
    }

    override fun getMobile(): Resource<String> {
        return Resource.success(mPrefs.getString(PREF_KEY_MOBILE,null) ?: "",200)
    }
    
    override fun setMobile(mobile: String) {
        mPrefs.edit()
            .putString(PREF_KEY_MOBILE, mobile)
            .apply()
    }

    override fun getPassword(): String? {
        return mPrefs.getString(PREF_KEY_PASSWORD,null)
    }

    override fun setPassword(password: String?) {
        mPrefs.edit()
            .putString(PREF_KEY_PASSWORD, password)
            .apply()
    }

    override fun setEncodedCipherIv(iv: String) {
        mPrefs.edit()
            .putString(PREF_KEY_IV, iv)
            .apply()
    }

    override fun getEncodedCipherIv(): String? {
        return mPrefs.getString(PREF_KEY_IV,null)
    }

    override fun clearToken() : Boolean {
        return mPrefs.edit().remove(PREF_KEY_ACCESS_TOKEN).remove(PREF_KEY_REFRESH_TOKEN).commit()
    }

    override fun setNationalCode(code: String) {
        mPrefs.edit()
            .putString(PREF_KEY_NATIONAL_CODE, code)
            .apply()
    }

    override fun getNationalCode(): String {
        return mPrefs.getString(PREF_KEY_NATIONAL_CODE,null) ?: ""
    }

    override fun setAppLink(appLink: String) {
        mPrefs.edit()
            .putString(PREF_KEY_APP_LINK, appLink)
            .apply()
    }

    override fun getAppLink(): Resource<GetAppLinkRepoModel> {
        return Resource.success(GetAppLinkRepoModel(mPrefs.getString(PREF_KEY_APP_LINK,null) ?: ""),200)

    }
    companion object {
        private const val PREF_KEY_ACCESS_TOKEN: String = "PREF_KEY_ACCESS_TOKEN"
        private const val PREF_KEY_REFRESH_TOKEN: String = "PREF_KEY_REFRESH_TOKEN"
        private const val PREF_KEY_MOBILE: String = "PREF_KEY_MOBILE"
        private const val PREF_KEY_NATIONAL_CODE: String = "PREF_KEY_NATIONAL_CODE"
        private const val PREF_KEY_PASSWORD: String = "PREF_KEY_PASSWORD"
        private const val PREF_KEY_IV: String = "PREF_KEY_IV"
        private const val PREF_KEY_APP_LINK: String = "PREF_KEY_APP_LINK"
    }

}
