package com.paya.data.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module(
	includes = [
		DeveloperNameModule::class,
		AuthModule::class,
		ApiServiceFactoryModule::class,
		ObjectBoxDeveloperNameFactoryModule::class,
		AccessTokenDbModule::class
	]
)
@InstallIn(ActivityRetainedComponent::class)
abstract class DataModule