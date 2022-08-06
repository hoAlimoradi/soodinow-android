package com.paya.data.di

import com.paya.common.Mapper
import com.paya.data.mapper.FarabiAuthRemoteRepoMapper
import com.paya.data.mapper.FarabiInfoRemoteRepoMapper
import com.paya.data.mapper.UserFarabiRemoteRepoMapper

import com.paya.data.repository.FarabiRepositoryImpl
import com.paya.domain.models.repo.*
import com.paya.domain.repository.FarabiRepository
import com.paya.domain.tools.UseCase
import com.paya.domain.usecase.farabi.FarabiAuthUseCase
import com.paya.domain.usecase.farabi.FarabiInfoUseCase
import com.paya.domain.usecase.farabi.UserFarabiUseCase

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class FarabiModule {
	
	@Binds
	abstract fun bindFarabiAuthRemoteRepoMapper(mapper: FarabiAuthRemoteRepoMapper): Mapper<
			String,
			FarabiTokenRepoModel
			>
	@Binds
	abstract fun bindFarabiInfoRemoteRepoMapper(mapper: FarabiInfoRemoteRepoMapper): Mapper<
			String,
			FarabiInfoRepoModel
			>
	@Binds
	abstract fun bindUserFarabiRemoteRepoMapper(mapper: UserFarabiRemoteRepoMapper): Mapper<
			List<*>,
			UserFarabiRepoModel
			>
	@Binds
	abstract fun farabiRepo(dev: FarabiRepositoryImpl): FarabiRepository
	@Binds
	abstract fun bindFarabiAuthUseCase(useCase: FarabiAuthUseCase): UseCase<String, FarabiTokenRepoModel>
	@Binds
	abstract fun bindFarabiInfoUseCase(useCase: FarabiInfoUseCase): UseCase<UserFarabiRepoModel, FarabiInfoRepoModel>
	@Binds
	abstract fun bindUserFarabiUseCase(useCase: UserFarabiUseCase): UseCase<String, UserFarabiRepoModel>
	
}