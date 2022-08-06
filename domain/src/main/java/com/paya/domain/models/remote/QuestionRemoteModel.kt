package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class QuestionRemoteModel(
	@SerializedName("title")
	val title: String,
	@SerializedName("slug")
	val slug: String
): NoObfuscate
