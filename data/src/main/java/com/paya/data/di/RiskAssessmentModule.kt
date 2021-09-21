package com.paya.data.di

import com.paya.data.repository.RiskAssessmentRepositoryImpl
import com.paya.domain.models.remote.RiskAssessmentPages
import com.paya.domain.models.remote.RiskAssessmentResponseRemoteModel
import com.paya.domain.repository.RiskAssessmentRepository
import com.paya.domain.tools.UseCase
import com.paya.domain.usecase.riskAssessment.GetRiskAssessmentQuestionsUseCase
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
    abstract fun bindGetRiskAssessmentQuestionsUseCase(useCase: GetRiskAssessmentQuestionsUseCase): UseCase<Unit, RiskAssessmentResponseRemoteModel>

}