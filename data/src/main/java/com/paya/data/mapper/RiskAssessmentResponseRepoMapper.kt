package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.RiskAssessmentQuestionRemoteModel
import com.paya.domain.models.remote.RiskAssessmentResponseRemoteModel
import com.paya.domain.models.repo.RiskAssessmentPagesRepoModel
import com.paya.domain.models.repo.RiskAssessmentQuestionRepoModel
import com.paya.domain.models.repo.RiskAssessmentResponseRepoModel
import javax.inject.Inject

class RiskAssessmentResponseRepoMapper @Inject constructor() : Mapper<
        RiskAssessmentResponseRemoteModel,
        RiskAssessmentResponseRepoModel
        > {
    override fun map(param: RiskAssessmentResponseRemoteModel): RiskAssessmentResponseRepoModel {

 /*       val pages: List<RiskAssessmentPagesRepoModel>
        val questions: List<RiskAssessmentQuestionRemoteModel>
        var pageNumberRepo = 0
        var questionCountRepo = 0 */


        val paramRepo = mutableListOf<RiskAssessmentPagesRepoModel>()
        val paramPagesSize = param.pages.size - 1
        for (paramPagesCounter in 0..paramPagesSize) {

            val questionsSize = param.pages[paramPagesCounter].questions.size - 1
            val questionsRepo = mutableListOf<RiskAssessmentQuestionRepoModel>()
            for (counter in 0..questionsSize) {
                val questionRepo = RiskAssessmentQuestionRepoModel(
                    id = param.pages[paramPagesCounter].questions[counter].id,
                    title = param.pages[paramPagesCounter].questions[counter].title,
                    type = param.pages[paramPagesCounter].questions[counter].type,
                    answers = param.pages[paramPagesCounter].questions[counter].answers
                )
                questionsRepo.add(questionRepo)
                //questionsRepo.plus(questionRepo)
            }

            val page = RiskAssessmentPagesRepoModel(
                pageNumber = param.pages[paramPagesCounter].pageNumber,
                questionCount = param.pages[paramPagesCounter].questionCount,
                questions = questionsRepo
            )

            paramRepo.add(page)
        }

        ///
       /* param.pages.forEach { page ->

            page.questions.forEach { question ->
                 val questionRepo = RiskAssessmentQuestionRepoModel(
                    id = question.id,
                    title = question.title,
                    type = question.type,
                    answers = question.answers
                )
                questionsRepo.add(questionRepo)
               // questionsRepo.plus(questionRepo)
            }


            val page = RiskAssessmentPagesRepoModel(
                pageNumber = page.pageNumber,
                questionCount = page.questionCount,
                questions = questionsRepo
            )

            paramRepo.add(page)
        }*/

        return RiskAssessmentResponseRepoModel(
            pages = paramRepo,
            count = param.count,
            pageCount = param.pageCount
        )
    }
}
