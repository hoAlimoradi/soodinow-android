package com.example.data.di

import com.example.data.database.ObjectBox
import com.example.data.network.ApiServiceFactory
import com.example.domain.models.local.DeveloperNameDbModel
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import io.objectbox.Box

@Module
@InstallIn(ActivityRetainedComponent::class)
class ObjectBoxDeveloperNameFactoryModule{
	@Provides
	@Reusable
	fun developerNameService(): Box<DeveloperNameDbModel> =
		ObjectBox.boxStore.boxFor(DeveloperNameDbModel::class.java)
}