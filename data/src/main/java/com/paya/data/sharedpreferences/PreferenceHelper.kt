package com.paya.data.sharedpreferences

interface PreferenceHelper {

    fun getAccessToken(): String

    fun setAccessToken(accessToken: String)
}