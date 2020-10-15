package com.paya.data.di

import com.paya.common.Mapper
import com.paya.data.database.developer_name.DeveloperNameDbApi
import com.paya.data.database.developer_name.DeveloperNameDbApiImpl
import com.paya.data.mapper.DeveloperNameEntityRepoMapper
import com.paya.data.mapper.DeveloperNameRepoEntityMapper
import com.paya.data.mapper.DeveloperNameServerRepoMapper
import com.paya.data.repository.DeveloperRepositoryImpl
import com.paya.domain.models.local.DeveloperNameDbModel
import com.paya.domain.models.remote.DeveloperNameServerModel
import com.paya.domain.models.repo.DeveloperNameRepoModel
import com.paya.domain.repository.DeveloperRepository
import com.paya.domain.tools.UseCase
import com.paya.domain.usecase.developer_name.GetDeveloperNameCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class DeveloperNameModule {
	
	@Binds
	abstract fun bindMapper(dev: DeveloperNameServerRepoMapper): Mapper<
			DeveloperNameServerModel,
			DeveloperNameRepoModel
			>
	
	@Binds
	abstract fun bindMapperRepoEntity(devRepoEntityMapper: DeveloperNameRepoEntityMapper): Mapper<
			DeveloperNameRepoModel?,
			DeveloperNameDbModel
			>
	
	@Binds
	abstract fun bindMapperEntityRepo(devEntityRepoMapper: DeveloperNameEntityRepoMapper): Mapper<
			DeveloperNameDbModel?,
			DeveloperNameRepoModel
			>
	
	@Binds
	abstract fun developerNameRepo(dev : DeveloperRepositoryImpl): DeveloperRepository
	
	@Binds
	abstract fun developerNameDb(db: DeveloperNameDbApiImpl): DeveloperNameDbApi
	
	@Binds
	abstract fun bindGetDeveloperNameCase(db: GetDeveloperNameCase): UseCase<Unit,DeveloperNameRepoModel>
}