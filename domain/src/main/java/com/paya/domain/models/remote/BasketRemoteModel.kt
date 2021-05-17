package com.paya.domain.models.remote

import com.paya.domain.tools.NoObfuscate

data class BasketRemoteModel (
	val name: String,
	val namad: String,
	val percent: Float,
	val color : String
): NoObfuscate
