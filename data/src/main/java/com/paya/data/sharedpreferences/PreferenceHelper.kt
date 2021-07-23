package com.paya.data.sharedpreferences

import com.paya.domain.tools.Resource

interface PreferenceHelper {

    fun getAccessToken(): String

    fun isLogin(): Boolean

    fun getRefreshToken(): String

    fun setAccessToken(accessToken: String)

    fun setRefreshToken(token: String)

    fun getMobile(): Resource<String>

    fun setMobile(mobile: String)

    fun getPassword(): String?

    fun setPassword(password: String?)

    fun setEncodedCipherIv(iv: String)

    fun getEncodedCipherIv(): String?

    fun clearToken(): Boolean

    fun setNationalCode(code: String)

    fun getNationalCode(): String
}