package com.paya.data.network.interceptor

import com.paya.data.database.userInfo.UserInfoDbApi
import kotlinx.coroutines.*
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthenticatorInterceptor @Inject constructor(
    private val userInfoDbApi: UserInfoDbApi
): Interceptor {
    
    private var credentials: String? = null
    
    override fun intercept(chain: Interceptor.Chain): Response {
        runBlocking {
            credentials = userInfoDbApi.getSingle()?.accessToken
        }
        var request = chain.request()
        credentials?.let {
            request = request.newBuilder().header(
                "Authorization",
                "Bearer $credentials"
            ).build()
        }
        
        return chain.proceed(request)
    }
    
}