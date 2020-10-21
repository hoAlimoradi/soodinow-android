package com.paya.data.di

import com.paya.common.Mapper
import com.paya.data.mapper.*
import com.paya.data.repository.AuthRepositoryImpl
import com.paya.domain.models.remote.AccessTokenRemoteModel
import com.paya.domain.models.remote.ActivateRemoteModel
import com.paya.domain.models.remote.RegisterRemoteModel
import com.paya.domain.models.repo.AccessTokenRepoModel
import com.paya.domain.models.repo.ActivateRepoModel
import com.paya.domain.models.repo.RegisterRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.UseCase
import com.paya.domain.usecase.auth.ActivateUseCase
import com.paya.domain.usecase.auth.RegisterUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class AuthModule {
	
	@Binds
	abstract fun bindMapperRemoteRepo(mapper: RegisterRemoteRepoMapper): Mapper<
			RegisterRemoteModel,
			RegisterRepoModel
			>
	
	@Binds
	abstract fun bindActivateRemoteRepoMapper(mapper: ActivateRemoteRepoMapper): Mapper<
			ActivateRemoteModel,
			ActivateRepoModel
			>
	
//	@Binds
//	abstract fun bindAccessTokenRemoteRepoMapper(mapper: AccessTokenRemoteRepoMapper): Mapper<
//			AccessTokenRemoteModel,
//			AccessTokenRepoModel
//			>
	
	@Binds
	abstract fun registerRepo(dev: AuthRepositoryImpl): AuthRepository
	
	@Binds
	abstract fun bindRegisterUseCase(useCase: RegisterUseCase): UseCase<String,RegisterRepoModel>
	
	@Binds
	abstract fun bindActivateUseCase(useCase: ActivateUseCase): UseCase<ActivateRepoModel,ActivateRepoModel>
}