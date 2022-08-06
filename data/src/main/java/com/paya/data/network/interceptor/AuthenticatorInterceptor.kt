package com.paya.data.network.interceptor

import com.paya.data.network.remote_api.AuthService
import com.paya.data.sharedpreferences.PreferenceHelper
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthenticatorInterceptor @Inject constructor(
    private val preferenceHandler: PreferenceHelper,
    private val authService: AuthService
) : Authenticator {


    override fun authenticate(route: Route?, response: Response): Request? {
        synchronized(this) {
            try {
                val refreshToken =
                    authService.refreshToken(
                        preferenceHandler.getRefreshToken()
                    ).execute()
                if (refreshToken.isSuccessful) {
                    refreshToken.body()?.let { body ->
                        body.data.accessToken?.let { accessToken ->
                            preferenceHandler.setAccessToken(accessToken)
                            return response.request.newBuilder().header(
                                "Authorization",
                                "Bearer $accessToken"
                            ).build()
                        }
                    }
                } else {
                    preferenceHandler.setAccessToken("")
                    preferenceHandler.setRefreshToken("")
                }
            } catch (e: Exception) {
                preferenceHandler.setAccessToken("")
                preferenceHandler.setRefreshToken("")
            }
            return null
        }
    }


}