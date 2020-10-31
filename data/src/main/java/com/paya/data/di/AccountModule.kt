package com.paya.data.di

import com.paya.common.Mapper
import com.paya.data.mapper.*
import com.paya.data.repository.AccountRepositoryImpl
import com.paya.domain.models.remote.AccountCardRemoteModel
import com.paya.domain.models.remote.RegisterRemoteModel
import com.paya.domain.models.repo.*
import com.paya.domain.repository.AccountRepository
import com.paya.domain.tools.UseCase
import com.paya.domain.usecase.account.GetAccountUseCase
import com.paya.domain.usecase.auth.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class AccountModule {
	
	@Binds
	abstract fun bindGetAccountMapperRemoteRepo(mapper: AccountCardRemoteRepoMapper): Mapper<
			AccountCardRemoteModel,
			AccountCardRepoModel
			>
	
	@Binds
	abstract fun accountRepo(dev: AccountRepositoryImpl): AccountRepository
	
	
	
	@Binds
	abstract fun bindGetAccountUseCase(useCase: GetAccountUseCase): UseCase<Unit,AccountCardRepoModel>
	
}