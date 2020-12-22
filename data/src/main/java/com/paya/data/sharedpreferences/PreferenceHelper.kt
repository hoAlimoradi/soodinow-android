package com.paya.data.sharedpreferences

import com.paya.domain.tools.Resource

interface PreferenceHelper {

    fun getAccessToken(): String

    fun setAccessToken(accessToken: String)
    
    fun getMobile() : Resource<String>
    
    fun setMobile(mobile: String)

    fun getPassword(): String?

    fun setPassword(password: String?)

    fun setEncodedCipherIv(iv: String)

    fun getEncodedCipherIv(): String?
}