package com.paya.domain.models.repo

data class QuestionsRepoModel(
	val title: String,
	val slug: String,
	val type: String,
	val answers: List<QuestionRepoModel>,
	var selectedAnswer: MutableList<Int> = mutableListOf(),
	val values: MutableMap<Int, String?> = mutableMapOf()
)