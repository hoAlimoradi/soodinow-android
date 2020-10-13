package com.example.data.network

import com.chuckerteam.chucker.BuildConfig
import com.example.data.network.interceptor.ChuckIntercept
import com.example.data.network.interceptor.HttpLogIntercept
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

const val TIME_OUT: Long = 60
const val BaseUrl: String = "http://demo9484242.mockable.io/"

class ApiServiceFactory @Inject constructor(
	private val chuckIntercept : ChuckIntercept,
	private val httpLogIntercept : HttpLogIntercept
) {
	
	fun <T> create(serviceClass: Class<T>): T = retrofit().create(serviceClass)
	
	private fun retrofit(): Retrofit = Retrofit.Builder()
		.baseUrl(BaseUrl)
		.addConverterFactory(MoshiConverterFactory.create())
		.addCallAdapterFactory(CoroutineCallAdapterFactory())
		.client(okHttpClientBuilder().build())
		.build()
	
	private fun okHttpClientBuilder() : OkHttpClient.Builder {
		val builder = OkHttpClient.Builder()
			.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
			.writeTimeout(TIME_OUT, TimeUnit.SECONDS)
			.readTimeout(TIME_OUT, TimeUnit.SECONDS)
			.retryOnConnectionFailure(true)
			.addInterceptor(httpLogIntercept.getIntercept())
		if (BuildConfig.DEBUG) {
			builder.addInterceptor(chuckIntercept.getIntercept())
		}
		return builder
	}
}