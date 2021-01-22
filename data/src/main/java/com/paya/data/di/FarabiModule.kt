package com.paya.data.di

import com.paya.common.Mapper
import com.paya.data.mapper.FarabiAuthRemoteRepoMapper
import com.paya.data.mapper.QuestionsRemoteRepoMapper
import com.paya.data.mapper.UserTestRemoteRepoMapper
import com.paya.data.repository.AccountRepositoryImpl
import com.paya.data.repository.FarabiRepositoryImpl
import com.paya.data.repository.QuestionsRepositoryImpl
import com.paya.domain.models.remote.QuestionsRemoteModel
import com.paya.domain.models.remote.UserTestBody
import com.paya.domain.models.remote.UserTestRemoteModel
import com.paya.domain.models.repo.FarabiTokenRepoModel
import com.paya.domain.models.repo.QuestionsRepoModel
import com.paya.domain.models.repo.UserTestRepoModel
import com.paya.domain.repository.AccountRepository
import com.paya.domain.repository.FarabiRepository
import com.paya.domain.repository.QuestionsRepository
import com.paya.domain.tools.UseCase
import com.paya.domain.usecase.farabi.FarabiAuthUseCase
import com.paya.domain.usecase.question.GetAllQuestionsUseCase
import com.paya.domain.usecase.question.SubmitTestUseCase
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
	abstract fun farabiRepo(dev: FarabiRepositoryImpl): FarabiRepository
	@Binds
	abstract fun bindFarabiAuthUseCase(useCase: FarabiAuthUseCase): UseCase<String, FarabiTokenRepoModel>
	
}