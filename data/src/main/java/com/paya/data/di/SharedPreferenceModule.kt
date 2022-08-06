package com.paya.data.di

import com.paya.data.sharedpreferences.AppPreferencesHelper
import com.paya.data.sharedpreferences.PreferenceHelper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SharedPreferenceModule {
	
	@Binds
	abstract fun bindSharedPreferences(preferencesHelper: AppPreferencesHelper): PreferenceHelper
	
}