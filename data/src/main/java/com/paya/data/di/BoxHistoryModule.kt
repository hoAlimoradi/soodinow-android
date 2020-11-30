package com.paya.data.di

import com.paya.common.Mapper
import com.paya.data.mapper.BoxHistoryRemoteRepoMapper
import com.paya.data.repository.BoxHistoryRepositoryImpl
import com.paya.domain.models.remote.BoxHistoryRemoteModel
import com.paya.domain.models.repo.BoxHistoryRepoModel
import com.paya.domain.models.repo.BoxHistoryRequestModel
import com.paya.domain.repository.BoxHistoryRepository
import com.paya.domain.tools.UseCase
import com.paya.domain.usecase.boxHistory.GetBoxHistoryUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class BoxHistoryModule {
	
	@Binds
	abstract fun bindBixHistoryMapper(mapper: BoxHistoryRemoteRepoMapper): Mapper<
			BoxHistoryRemoteModel,
			BoxHistoryRepoModel>
	
	@Binds
	abstract fun bindBoxHistoryRepository(repo: BoxHistoryRepositoryImpl): BoxHistoryRepository
	
	@Binds
	abstract fun bindBoxHistoryUseCase(useCase: GetBoxHistoryUseCase): UseCase<
			BoxHistoryRequestModel,
			BoxHistoryRepoModel>
	
}