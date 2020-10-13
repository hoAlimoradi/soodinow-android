package com.example.data.di

import com.example.data.network.ApiServiceFactory
import com.example.data.network.remote_api.DeveloperNameService
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
}