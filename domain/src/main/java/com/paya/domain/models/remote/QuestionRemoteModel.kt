package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName

data class QuestionRemoteModel(
	@SerializedName("title")
	val title: String,
	@SerializedName("slug")
	val slug: String
)
