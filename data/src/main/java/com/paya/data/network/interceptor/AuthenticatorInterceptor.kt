package com.paya.data.network.interceptor

import com.paya.data.database.accessToken.AccessTokenDbApi
import kotlinx.coroutines.*
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthenticatorInterceptor @Inject constructor(
    accessTokenDbApi: AccessTokenDbApi
): Interceptor {
    
    private val scope = CoroutineScope(Dispatchers.IO)
    private var credentials: String? = null
    
    init {
    	scope.launch {
            credentials = accessTokenDbApi.getSingle()?.accessToken
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        credentials?.let {
            request = request.newBuilder().header(
                "Authorization",
                "Bearer $credentials"
            ).build()
        }
        
        if (scope.isActive)
            scope.cancel()
        
        return chain.proceed(request)
    }
    
}