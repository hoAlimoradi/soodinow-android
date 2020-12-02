package com.paya.data.di

import com.paya.data.network.ApiServiceFactory
import com.paya.data.network.remote_api.*
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
	
	@Provides
	@Reusable
	fun questionsService(apiService: ApiServiceFactory) =
		apiService.create(QuestionService::class.java)
	
	@Provides
	@Reusable
	fun lowRiskService(apiService: ApiServiceFactory) =
		apiService.create(LowRiskInvestmentService::class.java)
	
	@Provides
	@Reusable
	fun lowBoxHistoryService(apiService: ApiServiceFactory) =
		apiService.create(BoxHistoryService::class.java)
	
	@Provides
	@Reusable
	fun currencyPriceService(apiService: ApiServiceFactory) =
		apiService.create(CurrencyPriceService::class.java)
}