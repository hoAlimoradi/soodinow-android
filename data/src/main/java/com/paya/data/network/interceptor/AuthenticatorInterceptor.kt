package com.paya.data.network.interceptor

import com.paya.data.sharedpreferences.PreferenceHelper
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthenticatorInterceptor @Inject constructor(
    private val preferenceHelper: PreferenceHelper
): Interceptor {
    
    
    override fun intercept(chain: Interceptor.Chain): Response {
        val credentials = preferenceHelper.getAccessToken()
        var request = chain.request()
        request = request.newBuilder().header(
            "Authorization",
            "Bearer $credentials"
        ).build()
        
        return chain.proceed(request)
    }
    
}