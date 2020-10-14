package com.paya.data.di

import com.paya.data.database.ObjectBox
import com.paya.data.network.ApiServiceFactory
import com.paya.domain.models.local.DeveloperNameDbModel
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