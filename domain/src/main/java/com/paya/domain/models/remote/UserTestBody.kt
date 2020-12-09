package com.paya.domain.models.remote

data class UserTestBody(
	val q: String,
	val slug: String?,
	private val value: String?
)