package com.paya.data.network


import com.paya.data.BuildConfig
import com.paya.data.network.interceptor.ChuckIntercept
import com.paya.data.network.interceptor.HttpLogIntercept
import com.paya.data.network.apiresponse.ApiResponseCallAdapterFactory
import com.paya.data.network.interceptor.AuthenticatorInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

const val TIME_OUT: Long = 60

const val BaseUrl: String = "http://api.soodinow.com/api/v1/"


class ApiServiceFactory @Inject constructor(
	private val chuckIntercept : ChuckIntercept,
	private val httpLogIntercept : HttpLogIntercept,
	private val authenticatorInterceptor: AuthenticatorInterceptor
) {
	
	fun <T> create(serviceClass: Class<T>): T = retrofit().create(serviceClass)
	
	private fun retrofit(): Retrofit = Retrofit.Builder()
		.baseUrl(BaseUrl)
		.addConverterFactory(GsonConverterFactory.create())
		.addCallAdapterFactory(ApiResponseCallAdapterFactory())
		.client(okHttpClientBuilder().build())
		.build()
	
	private fun okHttpClientBuilder() : OkHttpClient.Builder {
		val builder = OkHttpClient.Builder()
			.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
			.writeTimeout(TIME_OUT, TimeUnit.SECONDS)
			.readTimeout(TIME_OUT, TimeUnit.SECONDS)
			.retryOnConnectionFailure(true)
			.addInterceptor(httpLogIntercept.getIntercept())
			.addInterceptor(authenticatorInterceptor)
		if (BuildConfig.DEBUG) {
			builder.addInterceptor(chuckIntercept.getIntercept())
		}
		return builder
	}
}