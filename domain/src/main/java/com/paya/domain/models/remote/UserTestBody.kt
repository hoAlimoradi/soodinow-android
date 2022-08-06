package com.paya.domain.models.remote

import com.paya.domain.tools.NoObfuscate

data class UserTestBody(
	val q: String,
	val slug: String?,
	private val value: String?
): NoObfuscate