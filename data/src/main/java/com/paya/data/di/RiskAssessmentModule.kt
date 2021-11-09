package com.paya.data.di

import com.paya.common.Mapper
import com.paya.data.mapper.RiskAssessmentResponseRepoMapper
import com.paya.data.mapper.RiskAssessmentSubmitResponseRepoMapper
import com.paya.data.repository.RiskAssessmentRepositoryImpl
import com.paya.domain.models.remote.*
import com.paya.domain.models.repo.RiskAssessmentResponseRepoModel
import com.paya.domain.models.repo.RiskAssessmentSubmitResponseRepoModel
import com.paya.domain.repository.RiskAssessmentRepository
import com.paya.domain.tools.UseCase
import com.paya.domain.usecase.riskAssessment.GetRiskAssessmentQuestionsUseCase
import com.paya.domain.usecase.riskAssessment.SubmitRiskAssessmentAnswersQuestionsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class RiskAssessmentModule {

    @Binds
    abstract fun riskAssessmentRepository(dev: RiskAssessmentRepositoryImpl): RiskAssessmentRepository

    @Binds
    abstract fun bindGetRiskAssessmentQuestionsUseCase(useCase: GetRiskAssessmentQuestionsUseCase): UseCase<Unit, RiskAssessmentResponseRepoModel>

    @Binds
    abstract fun bindSubmitRiskAssessmentAnswersQuestionsUseCase(useCase: SubmitRiskAssessmentAnswersQuestionsUseCase): UseCase<RiskAssessmentRequestAnswerBodyModel,
            RiskAssessmentSubmitResponseRepoModel>

    @Binds
    abstract fun bindRiskAssessmentSubmitResponseRepoMapper(mapper: RiskAssessmentSubmitResponseRepoMapper): Mapper<RiskAssessmentSubmitResponseRemoteModel,
            RiskAssessmentSubmitResponseRepoModel>

    @Binds
    abstract fun bindRiskAssessmentResponseRepoMapper(mapper: RiskAssessmentResponseRepoMapper): Mapper<RiskAssessmentResponseRemoteModel,
            RiskAssessmentResponseRepoModel>

}
