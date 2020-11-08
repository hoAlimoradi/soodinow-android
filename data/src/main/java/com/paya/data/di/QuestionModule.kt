package com.paya.data.di

import com.paya.common.Mapper
import com.paya.data.mapper.QuestionsRemoteRepoMapper
import com.paya.data.repository.QuestionsRepositoryImpl
import com.paya.domain.models.remote.QuestionsRemoteModel
import com.paya.domain.models.repo.QuestionsRepoModel
import com.paya.domain.repository.QuestionsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class QuestionModule {
	
	@Binds
	abstract fun bindQuestionsRemoteRepoMapper(mapper: QuestionsRemoteRepoMapper): Mapper<
			QuestionsRemoteModel,
			QuestionsRepoModel
			>
	
	@Binds
	abstract fun bindQuestionRepository(repository: QuestionsRepositoryImpl): QuestionsRepository
	
}