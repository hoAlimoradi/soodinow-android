package com.paya.data.di

import com.paya.common.Mapper
import com.paya.data.database.accessToken.AccessTokenDbApi
import com.paya.data.database.accessToken.AccessTokenDbApiImpl
import com.paya.data.mapper.*
import com.paya.data.repository.AuthRepositoryImpl
import com.paya.domain.models.local.AccessTokenDbModel
import com.paya.domain.models.remote.AccessTokenRemoteModel
import com.paya.domain.models.remote.RegisterRemoteModel
import com.paya.domain.models.remote.SetPasswordRemoteModel
import com.paya.domain.models.repo.AccessTokenRepoModel
import com.paya.domain.models.repo.ActivateRepoModel
import com.paya.domain.models.repo.RegisterRepoModel
import com.paya.domain.models.repo.SetPasswordRepoModel
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.UseCase
import com.paya.domain.usecase.auth.ActivateUseCase
import com.paya.domain.usecase.auth.GetAccessTokenUseCase
import com.paya.domain.usecase.auth.RegisterUseCase
import com.paya.domain.usecase.auth.SetPasswordUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class AuthModule {
	
	@Binds
	abstract fun bindRegisterMapperRemoteRepo(mapper: RegisterRemoteRepoMapper): Mapper<
			RegisterRemoteModel,
			RegisterRepoModel
			>
	
	@Binds
	abstract fun bindAccessTokenRemoteRepoMapper(mapper: AccessTokenRemoteRepoMapper): Mapper<
			AccessTokenRemoteModel,
			AccessTokenRepoModel
			>
	
	@Binds
	abstract fun bindAccessTokenRepoEntityMapper(mapper: AccessTokenRepoEntityMapper): Mapper<
			AccessTokenRepoModel?,
			AccessTokenDbModel
			>
	
	@Binds
	abstract fun bindAccessTokenEntityRepoMapper(mapper: AccessTokenEntityRepoMapper): Mapper<
			AccessTokenDbModel?,
			AccessTokenRepoModel
			>
	
	@Binds
	abstract fun bindSetPasswordRemoteRepoMapper(mapper: SetPasswordRemoteRepoMapper): Mapper<
			SetPasswordRemoteModel,
			SetPasswordRepoModel
			>
	
	@Binds
	abstract fun registerRepo(dev: AuthRepositoryImpl): AuthRepository
	
	@Binds
	abstract fun accessTokenDbApi(api: AccessTokenDbApiImpl): AccessTokenDbApi
	
	@Binds
	abstract fun bindRegisterUseCase(useCase: RegisterUseCase): UseCase<String,RegisterRepoModel>
	
	@Binds
	abstract fun bindActivateUseCase(useCase: ActivateUseCase):
			UseCase<ActivateRepoModel,AccessTokenRepoModel>
	
	@Binds
	abstract fun bindGetAccessTokenUseCase(useCase: GetAccessTokenUseCase):
			UseCase<Unit,AccessTokenRepoModel>
	
	@Binds
	abstract fun bindSetPasswordUseCase(useCase: SetPasswordUseCase):
			UseCase<String,SetPasswordRepoModel>
}