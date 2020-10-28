package com.paya.data.di

import com.paya.data.network.ApiServiceFactory
import com.paya.data.network.remote_api.AccountService
import com.paya.data.network.remote_api.DeveloperNameService
import com.paya.data.network.remote_api.AuthService
import com.paya.data.network.remote_api.MarketService
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
class ApiServiceFactoryModule{
	@Provides
	@Reusable
	fun developerNameService(apiService: ApiServiceFactory) =
		apiService.create(DeveloperNameService::class.java)
	
	@Provides
	@Reusable
	fun registerService(apiService: ApiServiceFactory) =
		apiService.create(AuthService::class.java)
	
	@Provides
	@Reusable
	fun accountService(apiService: ApiServiceFactory) =
		apiService.create(AccountService::class.java)
	
	@Provides
	@Reusable
	fun marketService(apiService: ApiServiceFactory) =
		apiService.create(MarketService::class.java)
}