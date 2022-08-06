package com.paya.domain.models.remote

import com.paya.domain.tools.NoObfuscate

data class AddRiskOrderRemoteBodyModel(
	val type: String,
	val price: Long
): NoObfuscate