package com.paya.data.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module(
includes = [
	DeveloperNameModule::class,
	ApiServiceFactoryModule::class,
	ObjectBoxDeveloperNameFactoryModule::class
]
)
@InstallIn(ActivityRetainedComponent::class)
abstract class DataModule