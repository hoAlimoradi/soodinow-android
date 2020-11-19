package com.paya.data.di

import com.paya.common.Mapper
import com.paya.data.database.userInfo.UserInfoDbApi
import com.paya.data.database.userInfo.UserInfoDbApiImpl
import com.paya.data.mapper.*
import com.paya.data.repository.AuthRepositoryImpl
import com.paya.domain.models.local.UserInfoDbModel
import com.paya.domain.models.remote.*
import com.paya.domain.models.repo.*
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.UseCase
import com.paya.domain.usecase.auth.*
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
			UserInfoRepoModel
			>
	
	@Binds
	abstract fun bindAccessTokenRepoEntityMapper(mapper: AccessTokenRepoEntityMapper): Mapper<
			UserInfoRepoModel?,
			UserInfoDbModel
			>
	
	@Binds
	abstract fun bindAccessTokenEntityRepoMapper(mapper: AccessTokenEntityRepoMapper): Mapper<
			UserInfoDbModel?,
			UserInfoRepoModel
			>
	
	@Binds
	abstract fun bindSetPasswordRemoteRepoMapper(mapper: SetPasswordRemoteRepoMapper): Mapper<
			SetPasswordRemoteModel,
			SetPasswordRepoModel
			>
	
	@Binds
	abstract fun bindProfileRemoteRepoMapper(mapper: ProfileRemoteRepoMapper): Mapper<
			ProfileRemoteModel,
			ProfileRepoModel
			>
	
	@Binds
	abstract fun bindUpdateProfileRepoRemoteMapper(mapper: ProfileRepoRemoteMapper): Mapper<
			ProfileBodyRepoModel,
			ProfileBodyRemoteModel
			>
	
	@Binds
	abstract fun registerRepo(dev: AuthRepositoryImpl): AuthRepository
	
	@Binds
	abstract fun accessTokenDbApi(api: UserInfoDbApiImpl): UserInfoDbApi
	
	@Binds
	abstract fun bindRegisterUseCase(useCase: RegisterUseCase): UseCase<String,RegisterRepoModel>
	
	@Binds
	abstract fun bindActivateUseCase(useCase: ActivateUseCase):
			UseCase<ActivateRepoModel,Any>
	
	@Binds
	abstract fun bindLoginUseCase(useCase: LoginUseCase):
			UseCase<LoginRepoModel,Any>
	
	@Binds
	abstract fun bindGetAccessTokenUseCase(useCase: GetUserInfoUseCase):
			UseCase<Unit,UserInfoRepoModel>
	
	@Binds
	abstract fun bindSetPasswordUseCase(useCase: SetPasswordUseCase):
			UseCase<String,SetPasswordRepoModel>
	
	@Binds
	abstract fun bindUpdateProfileUseCase(useCase: UpdateProfileUseCase):
			UseCase<ProfileBodyRepoModel,ProfileRepoModel>
	
	@Binds
	abstract fun bindGetProfileUseCase(useCase: GetProfileUseCase):
			UseCase<Unit,ProfileRepoModel>
}