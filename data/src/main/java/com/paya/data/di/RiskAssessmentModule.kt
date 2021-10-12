package com.paya.data.di

import com.paya.common.Mapper
import com.paya.data.mapper.RiskAssessmentSubmitResponseRepoMapper
import com.paya.data.mapper.WalletBuyRemoteRepoMapper
import com.paya.data.repository.RiskAssessmentRepositoryImpl
import com.paya.domain.models.remote.*
import com.paya.domain.models.repo.WalletBuyRepoModel
import com.paya.domain.repository.RiskAssessmentRepository
import com.paya.domain.tools.UseCase
import com.paya.domain.usecase.riskAssessment.GetRiskAssessmentQuestionsUseCase
import com.paya.domain.usecase.riskAssessment.SubmitRiskAssessmentAnswersQuestionsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject

@Module
@InstallIn(ActivityComponent::class)
abstract class RiskAssessmentModule {

    @Binds
    abstract fun riskAssessmentRepository(dev: RiskAssessmentRepositoryImpl): RiskAssessmentRepository

    @Binds
    abstract fun bindGetRiskAssessmentQuestionsUseCase(useCase: GetRiskAssessmentQuestionsUseCase): UseCase<Unit, RiskAssessmentResponseRemoteModel>

    @Binds
    abstract fun bindSubmitRiskAssessmentAnswersQuestionsUseCase(useCase: SubmitRiskAssessmentAnswersQuestionsUseCase): UseCase<RiskAssessmentRequestAnswerRepoBodyModel,
            RiskAssessmentSubmitResponseRepoModel>

    @Binds
    abstract fun bindRiskAssessmentSubmitResponseRepoMapper(mapper: RiskAssessmentSubmitResponseRepoMapper): Mapper<RiskAssessmentSubmitResponseRemoteModel,
            RiskAssessmentSubmitResponseRepoModel>

}
