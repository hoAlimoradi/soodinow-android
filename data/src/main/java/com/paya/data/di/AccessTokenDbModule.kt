package com.paya.data.di

import com.paya.data.database.ObjectBox
import com.paya.domain.models.local.UserInfoDbModel
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import io.objectbox.Box

@Module
@InstallIn(ActivityRetainedComponent::class)
class AccessTokenDbModule{
	@Provides
	@Reusable
	fun accessTokenDb(): Box<UserInfoDbModel> =
		ObjectBox.boxStore.boxFor(UserInfoDbModel::class.java)
}