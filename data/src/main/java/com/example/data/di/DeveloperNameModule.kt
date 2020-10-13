package com.example.data.di

import com.example.common.Mapper
import com.example.data.database.developer_name.DeveloperNameDbApi
import com.example.data.database.developer_name.DeveloperNameDbApiImpl
import com.example.data.datasource.developer.DeveloperDataSource
import com.example.data.datasource.developer.DeveloperDataSourceImpl
import com.example.data.mapper.DeveloperNameEntityRepoMapper
import com.example.data.mapper.DeveloperNameRepoEntityMapper
import com.example.data.mapper.DeveloperNameServerRepoMapper
import com.example.data.repository.DeveloperRepositoryImpl
import com.example.domain.models.local.DeveloperNameDbModel
import com.example.domain.models.remote.DeveloperNameServerModel
import com.example.domain.models.repo.DeveloperNameRepoModel
import com.example.domain.repository.DeveloperRepository
import com.example.domain.tools.UseCase
import com.example.domain.usecase.developer_name.GetDeveloperNameCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
abstract class DeveloperNameModule{
	
	@Binds
	abstract fun bindMapper(dev : DeveloperNameServerRepoMapper): Mapper<
			DeveloperNameServerModel,
			DeveloperNameRepoModel
			>
	
	@Binds
	abstract fun bindMapperRepoEntity(devRepoEntityMapper : DeveloperNameRepoEntityMapper): Mapper<
			DeveloperNameRepoModel?,
			DeveloperNameDbModel
			>
	
	@Binds
	abstract fun bindMapperEntityRepo(devEntityRepoMapper : DeveloperNameEntityRepoMapper): Mapper<
			DeveloperNameDbModel?,
			DeveloperNameRepoModel
			>
	
	@Binds
	abstract fun developerNameRepo(dev : DeveloperRepositoryImpl): DeveloperRepository
	
	@Binds
	abstract fun developerName(dev: DeveloperDataSourceImpl): DeveloperDataSource
	
	@Binds
	abstract fun developerNameDb(db: DeveloperNameDbApiImpl): DeveloperNameDbApi
	
	@Binds
	abstract fun bindGetDeveloperNameCase(db: GetDeveloperNameCase): UseCase<Unit,DeveloperNameRepoModel>
}