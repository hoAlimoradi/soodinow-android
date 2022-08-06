package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.QuestionsRemoteModel
import com.paya.domain.models.repo.QuestionRepoModel
import com.paya.domain.models.repo.QuestionsRepoModel
import javax.inject.Inject

class QuestionsRemoteRepoMapper @Inject constructor() : Mapper<
		QuestionsRemoteModel,
		QuestionsRepoModel
		> {
	
	override fun map(param: QuestionsRemoteModel): QuestionsRepoModel {
		return QuestionsRepoModel(
			title = param.title,
			slug = param.slug,
			type = param.type,
			answers = param.answers.map { QuestionRepoModel(
				title = it.title,
				slug = it.slug
			) }
		)
	}
	
}