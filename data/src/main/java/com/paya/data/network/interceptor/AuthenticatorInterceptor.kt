package com.paya.data.network.interceptor

import com.paya.data.network.remote_api.AuthService
import com.paya.data.sharedpreferences.PreferenceHelper
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection
import javax.inject.Inject

class AuthenticatorInterceptor @Inject constructor(
    private val preferenceHandler: PreferenceHelper
) : Interceptor {
    var authService: AuthService? = null
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var response = chain.proceed(request)
        if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            synchronized(this) {
                    authService?.let { authService ->
                        val refreshToken =
                            authService.refreshToken(
                                preferenceHandler.getRefreshToken()
                            ).execute()
                        if (refreshToken.isSuccessful) {
                            refreshToken.body()?.let { body ->
                                body.data.accessToken?.let { accessToken ->
                                    preferenceHandler.setAccessToken(accessToken)
                                    request = request.newBuilder().header(
                                        "Authorization",
                                        "Bearer $accessToken"
                                    ).build()
                                    response.close()
                                    response = chain.proceed(request)
                                }
                            }
                        } else {
                            preferenceHandler.setAccessToken("")
                            preferenceHandler.setRefreshToken("")
                        }

                    }
            }
        }
        return response

    }


}