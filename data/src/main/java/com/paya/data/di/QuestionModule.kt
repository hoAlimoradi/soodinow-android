package com.paya.data.di

import com.paya.common.Mapper
import com.paya.data.mapper.QuestionsRemoteRepoMapper
import com.paya.data.mapper.UserTestRemoteRepoMapper
import com.paya.data.repository.QuestionsRepositoryImpl
import com.paya.domain.models.remote.QuestionsRemoteModel
import com.paya.domain.models.remote.UserTestBody
import com.paya.domain.models.remote.UserTestRemoteModel
import com.paya.domain.models.repo.QuestionsRepoModel
import com.paya.domain.models.repo.UserTestRepoModel
import com.paya.domain.repository.QuestionsRepository
import com.paya.domain.tools.UseCase
import com.paya.domain.usecase.question.GetAllQuestionsUseCase
import com.paya.domain.usecase.question.SubmitTestUseCase
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
	abstract fun bindUserTestMapper(mapper: UserTestRemoteRepoMapper): Mapper<
			UserTestRemoteModel,
			UserTestRepoModel
			>
	
	@Binds
	abstract fun bindQuestionRepository(repository: QuestionsRepositoryImpl): QuestionsRepository
	
	@Binds
	abstract fun binGetQuestionUseCase(useCase: GetAllQuestionsUseCase): UseCase<Unit,ArrayList<QuestionsRepoModel>>
	
	@Binds
	abstract fun bindSubmitTestUseCase(useCase: SubmitTestUseCase): UseCase<List<UserTestBody>, UserTestRepoModel>
	
}