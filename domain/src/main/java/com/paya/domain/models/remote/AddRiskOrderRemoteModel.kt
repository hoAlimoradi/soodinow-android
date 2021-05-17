package com.paya.domain.models.remote

import com.paya.domain.tools.NoObfuscate

data class AddRiskOrderRemoteModel(
	val items: List<AddRiskOrderItem>
): NoObfuscate