package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName

data class QuestionsRemoteModel(
	@SerializedName("title")
	val title: String,
	@SerializedName("slug")
	val slug: String,
	@SerializedName("type")
	val type: String,
	@SerializedName("answers")
	val answers: List<QuestionRemoteModel>
)